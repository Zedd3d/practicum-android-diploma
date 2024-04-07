package ru.practicum.android.diploma.presentation.general.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.app.App
import ru.practicum.android.diploma.domain.impl.VacanciesRepository
import ru.practicum.android.diploma.domain.models.Vacancy
import java.net.UnknownHostException
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

const val PAG_COUNT: Int = 20

class GeneralViewModel @Inject constructor(
    private val vacanciesRepository: VacanciesRepository,
    private val context : Context
) : ViewModel() {

    private val state = MutableStateFlow(ViewState())

    private var isNextPageLoading = false

    private var query: String? = null
    fun observeUi() = state.asStateFlow()

    fun search(query: String, page: Int = 0, isPagination: Boolean = false) {

        if(!isOnline(context)){
            state.update { it.copy(status = ResponseState.NetworkError, isLoading = false) }
            isNextPageLoading = false
            return
        }

        if (isNextPageLoading || this.query == query && !isPagination) return

        this.query = query

        if (query.isEmpty() && !isPagination) {
            state.update { it.copy(status = ResponseState.Start) }
            return
        }

        isNextPageLoading = true

        state.update { it.copy(isLoading = !isPagination) }

        asyncSearch(query, isPagination, page)
    }

    private fun asyncSearch(query: String, isPagination: Boolean, page: Int) {

        viewModelScope.launch {
            try {
                val response = vacanciesRepository.search(query, page)
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
                        isLoading = false,
                        status = if (vacancies.isNotEmpty()) ResponseState.Content else ResponseState.Empty
                    )
                }
            } catch (e: Throwable) {
                state.update { it.copy(status = ResponseState.ServerError) }
            } finally {
                state.update { it.copy(isLoading = false) }
                isNextPageLoading = false
            }
        }
    }

    fun onLastItemReached(query: String) {
        val page = state.value.vacancies.size / PAG_COUNT
        search(query, page, true)
    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities != null
    }
}

data class ViewState(
    val vacancies: List<Vacancy> = emptyList(),
    val status: ResponseState = ResponseState.Start,
    val found: Int = 0,
    val isLoading: Boolean = false
)

sealed class ResponseState {

    data object Start : ResponseState()
    data object Empty : ResponseState()
    data object Content : ResponseState()
    data object NetworkError : ResponseState()
    data object ServerError : ResponseState()
}
