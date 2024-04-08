package ru.practicum.android.diploma.presentation.filters.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.sharedpreferences.api.SharedPreferencesInteractor
import ru.practicum.android.diploma.presentation.filters.main.state.FiltersMainViewState
import javax.inject.Inject

class FiltersMainViewModel @Inject constructor(
    private val sharedPreferencesInteractor: SharedPreferencesInteractor
) : ViewModel() {

    private val state = MutableLiveData<FiltersMainViewState>()

    fun getState(): LiveData<FiltersMainViewState> = state

    private var query: String? = null

    init {
        state.postValue(getCurrentFilters())
    }

    fun getCurrentFilters(): FiltersMainViewState {
        val country = sharedPreferencesInteractor.getCountryFilter()?.valueString ?: ""
        val region = sharedPreferencesInteractor.getAreaFilter()?.valueString ?: ""
        val workplace = if (country.isEmpty()) region else "${country} ${region}"
        val industry = sharedPreferencesInteractor.getIndustryFilter()?.valueString ?: ""
        val salary = sharedPreferencesInteractor.getSalaryFilter()?.valueInt ?: 0
        val onlyWithSalary = sharedPreferencesInteractor.getOnlyWithSalaryFilter()?.valueBoolean ?: false

        return if (
            workplace.isEmpty()
            && industry.isEmpty()
            && salary == 0
            && onlyWithSalary == false
        ) {
            FiltersMainViewState.Empty
        } else {
            FiltersMainViewState.Content(
                workPlace = workplace,
                industries = industry,
                salary = salary,
                onlyWithSalary = onlyWithSalary
            )
        }

    }
}

sealed class ResponseState {

    data object Start : ResponseState()
    data object Empty : ResponseState()
    data object Content : ResponseState()
    data object NetworkError : ResponseState()
    data object ServerError : ResponseState()
}

