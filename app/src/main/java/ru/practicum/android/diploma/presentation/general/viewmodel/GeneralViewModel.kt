package ru.practicum.android.diploma.presentation.general.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.general.api.SearchVacanciesUseCase
import ru.practicum.android.diploma.domain.general.models.ResponseState
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.sharedpreferences.api.FiltersInteractor
import javax.inject.Inject

class GeneralViewModel @Inject constructor(
    private val searchVacanciesUseCase: SearchVacanciesUseCase,
    private val filtersInteractor: FiltersInteractor
) : ViewModel() {

    private val state = MutableLiveData<ResponseState>()

    private val stateFilters = MutableLiveData<Boolean>()

    private var currentListVacancies = emptyList<Vacancy>()

    fun observeUi(): LiveData<ResponseState> = state
    fun observeFilters(): LiveData<Boolean> = stateFilters

    private var isNextPageLoading = false

    private var filtersMap = emptyMap<String, String>()

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
        filtersMap = filtersInteractor.getAllFilters()
        viewModelScope.launch {
            when (val response = searchVacanciesUseCase(query, page, filtersMap)) {
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

                is ResponseState.NetworkError -> state.postValue(ResponseState.NetworkError(isPagination))

                is ResponseState.Loading -> state.postValue(ResponseState.Loading(isPagination))
                else -> {
                    state.postValue(response)
                }
            }
            stateFilters.postValue(filtersMap.isNotEmpty())
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

    fun searchOnFilterChanged() {
        if (!this.query.isNullOrEmpty()) {
            val query = this.query
            this.query = null
            query?.let { search(it) }
        }
    }

    fun updateHasFilters() {
        filtersMap = filtersInteractor.getAllFilters()
        stateFilters.postValue(filtersMap.isNotEmpty())
    }

    companion object {
        const val PAG_COUNT: Int = 20
    }
}
