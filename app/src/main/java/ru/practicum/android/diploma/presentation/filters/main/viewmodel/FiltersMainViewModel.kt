package ru.practicum.android.diploma.presentation.filters.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.sharedpreferences.api.FiltersInteractor
import ru.practicum.android.diploma.domain.sharedpreferences.model.SharedFilterNames
import ru.practicum.android.diploma.presentation.filters.main.state.FiltersMainViewState
import javax.inject.Inject

class FiltersMainViewModel @Inject constructor(
    private val sharedPreferencesInteractor: FiltersInteractor
) : ViewModel() {

    private val state = MutableLiveData<FiltersMainViewState>()

    fun getState(): LiveData<FiltersMainViewState> = state

    init {
        state.postValue(getCurrentFilters())
    }

    companion object {
        const val AREA = SharedFilterNames.AREA
        const val COUNTRY = SharedFilterNames.COUNTRY
        const val INDUSTRY = SharedFilterNames.INDUSTRY
        const val SALARY = SharedFilterNames.SALARY
        const val ONLY_WITH_SALARY = SharedFilterNames.ONLY_WITH_SALARY
    }

    fun getWorkPlace(): String {
        val country = sharedPreferencesInteractor.getFilter(SharedFilterNames.COUNTRY)?.valueString ?: ""
        val region = sharedPreferencesInteractor.getFilter(SharedFilterNames.AREA)?.valueString ?: ""
        return if (country.isEmpty()) region else "$country, $region"
    }

    fun getCurrentFilters(): FiltersMainViewState {
        val workplace = getWorkPlace()
        val industry = sharedPreferencesInteractor
            .getFilter(SharedFilterNames.INDUSTRY)?.valueString ?: ""
        val salary = sharedPreferencesInteractor
            .getFilter(SharedFilterNames.SALARY)?.valueInt ?: 0
        val onlyWithSalary = sharedPreferencesInteractor
            .getFilter(SharedFilterNames.ONLY_WITH_SALARY)?.valueBoolean ?: false

        return if (
            sharedPreferencesInteractor.getAllFilters().isEmpty()
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
