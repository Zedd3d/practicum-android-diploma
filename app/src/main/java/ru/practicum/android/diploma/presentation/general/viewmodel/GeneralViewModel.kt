package ru.practicum.android.diploma.presentation.general.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.general.models.ResponseState
import ru.practicum.android.diploma.domain.impl.SearchVacanciesUseCase
import ru.practicum.android.diploma.domain.models.Vacancy
import javax.inject.Inject

class GeneralViewModel @Inject constructor(
    private val searchVacanciesUseCase: SearchVacanciesUseCase,
) : ViewModel() {

    private val state = MutableLiveData<ResponseState>()

    private var currentListVacancies = emptyList<Vacancy>()

    fun observeUi(): LiveData<ResponseState> = state

    private var isNextPageLoading = false

    private var query: String? = null
        set(value) {
            maxPages = null
            field = value
        }

    private var maxPages: Int? = 0

    fun search(query: String, page: Int = 0) {
        if (this.query == query) return
        this.query = query

        if (query.isEmpty()) {
            state.postValue(ResponseState.Start)
            return
        }
        makeSearchRequest(query, page, false)
    }

    private fun makeSearchRequest(query: String, page: Int, isPagination: Boolean) {
        state.postValue(ResponseState.Loading(isPagination))
        viewModelScope.launch {
            val response = searchVacanciesUseCase(query, page)
            when (response) {
                is ResponseState.ContentVacanciesList -> {
                    maxPages = response.pages
                    currentListVacancies = if (isPagination) {
                        currentListVacancies + response.listVacancy
                    } else {
                        response.listVacancy
                    }

                    state.postValue(
                        ResponseState.ContentVacanciesList(
                            currentListVacancies,
                            response.found,
                            response.pages
                        )
                    )
                }

                is ResponseState.Loading -> state.postValue(ResponseState.Loading(isPagination))
                else -> {
                    state.postValue(response)
                }
            }
            isNextPageLoading = false
        }
    }

    private fun searchPagination(query: String, page: Int = 0) {
        if (isNextPageLoading) return
        maxPages?.let { if (page + 1 >= it) return }

        isNextPageLoading = true
        makeSearchRequest(query, page, true)
    }

    fun onLastItemReached() {
        val page = if (state.value is ResponseState.ContentVacanciesList) {
            (state.value as ResponseState.ContentVacanciesList).listVacancy.size / PAG_COUNT
        } else {
            1
        }
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
