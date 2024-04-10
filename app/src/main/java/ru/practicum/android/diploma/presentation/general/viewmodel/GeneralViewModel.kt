package ru.practicum.android.diploma.presentation.general.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import ru.practicum.android.diploma.domain.impl.SearchVacanciesUseCase
import ru.practicum.android.diploma.domain.models.Vacancy
import javax.inject.Inject

class GeneralViewModel @Inject constructor(
    private val searchVacanciesUseCase: SearchVacanciesUseCase,
    private val context: Context
) : ViewModel() {

    private val state = MutableStateFlow(ViewState())

    private var isNextPageLoading = false

    private var query: String? = null
        set(value) {
            maxPages = null
            field = value
        }

    private var maxPages: Int? = 0
    fun observeUi() = state.asStateFlow()

    fun search(query: String, page: Int = 0) {
        if (this.query == query) return
        this.query = query

        if (query.isEmpty()) {
            state.update { it.copy(status = ResponseState.Start) }
            return
        }

        when (isOnline(context)) {
            true -> {
                state.update { it.copy(isLoading = true, vacanciesProgress = false) }
                makeSearchRequest(query, page, false)
            }

            else -> {
                state.update {
                    it.copy(status = ResponseState.NetworkError)
                }
            }
        }

    }

    private fun makeSearchRequest(query: String, page: Int, isPagination: Boolean) {
        viewModelScope.launch {
            try {
                val response = searchVacanciesUseCase(query, page)
                maxPages = response.pages

                val vacancies = response.items
                val currentList = if (isPagination) {
                    state.value.vacancies + vacancies
                } else {
                    vacancies
                }

                state.update {
                    it.copy(
                        vacancies = currentList,
                        found = response.found,
                        status = if (currentList.isNotEmpty()) ResponseState.Content else ResponseState.Empty,
                    )
                }
            } catch (e: HttpException) {
                Log.d("NetError", e.code().toString())
                state.update { it.copy(status = ResponseState.ServerError) }
            } finally {
                isNextPageLoading = false
                state.update { it.copy(isLoading = false, vacanciesProgress = false) }
            }
        }
    }

    private fun searchPagination(query: String, page: Int = 0) {
        if (isNextPageLoading) return
        maxPages?.let { if (page + 1 >= it) return }

        isNextPageLoading = true
        state.update { it.copy(isLoading = false, vacanciesProgress = true) }

        makeSearchRequest(query, page, true)
    }

    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities != null
    }

    fun onLastItemReached() {
        val page = state.value.vacancies.size / PAG_COUNT
        query?.let { searchPagination(it, page) }
    }

    companion object {
        const val PAG_COUNT: Int = 20
    }
}

data class ViewState(
    val vacancies: List<Vacancy> = emptyList(),
    val status: ResponseState = ResponseState.Start,
    val found: Int = 0,
    val isLoading: Boolean = false,
    val vacanciesProgress: Boolean = false
)

sealed class ResponseState {

    data object Start : ResponseState()
    data object Empty : ResponseState()
    data object Content : ResponseState()
    data object NetworkError : ResponseState()
    data object ServerError : ResponseState()
}
