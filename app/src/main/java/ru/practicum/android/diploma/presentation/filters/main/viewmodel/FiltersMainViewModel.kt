package ru.practicum.android.diploma.presentation.filters.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.filters.models.FilterValue
import ru.practicum.android.diploma.domain.sharedpreferences.api.FiltersInteractor
import ru.practicum.android.diploma.domain.sharedpreferences.model.SharedFilterNames
import ru.practicum.android.diploma.presentation.filters.main.state.FiltersMainViewState
import javax.inject.Inject

class FiltersMainViewModel @Inject constructor(
    private val filtersInteractor: FiltersInteractor
) : ViewModel() {

    private val state = MutableLiveData<FiltersMainViewState>()

    fun getState(): LiveData<FiltersMainViewState> = state

    private val acceptAviable = MutableLiveData<Boolean>()

    fun getAcceptAviable(): LiveData<Boolean> = acceptAviable

    init {
        loadCurrentFilters()
    }

    fun getWorkPlace(): String {
        val array = mutableListOf<String>()
        filtersInteractor.getFilter(SharedFilterNames.COUNTRY)?.valueString?.let {
            array.add(it)
        }

        filtersInteractor.getFilter(SharedFilterNames.AREA)?.valueString?.let {
            array.add(it)
        }


        val result = array.joinToString(", ")

        return result
    }

    fun getCurrentFilters(): FiltersMainViewState {
        val workplace = getWorkPlace()
        val industry = filtersInteractor
            .getFilter(SharedFilterNames.INDUSTRY)?.valueString ?: ""
        val salary = filtersInteractor
            .getFilter(SharedFilterNames.SALARY)?.valueString ?: ""
        val onlyWithSalary =
            if (filtersInteractor.getFilter(SharedFilterNames.ONLY_WITH_SALARY) == null) {
                false
            } else {
                true
            }

        return if (
            filtersInteractor.getAllFilters().isEmpty()
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

    fun setSalaryFilter(filter: String) {
        if (filter == filtersInteractor.getFilter(SharedFilterNames.SALARY)?.valueString ?: "") return

        var filterValue: FilterValue? = null

        if (!filter.isNullOrEmpty()) {
            filterValue = FilterValue(
                "",
                "", SharedFilterNames.SALARY, filter
            )
        }
        filtersInteractor.setFilter(SharedFilterNames.SALARY, filterValue)
        acceptAviable.postValue(true)
    }

    fun setOnlySalaryFilter(checked: Boolean) {
        var filterValue: FilterValue? = null

        if (checked) {
            filterValue = FilterValue(
                "",
                "", SharedFilterNames.ONLY_WITH_SALARY, "true"
            )
        }
        filtersInteractor.setFilter(SharedFilterNames.ONLY_WITH_SALARY, filterValue)
        acceptAviable.postValue(true)
    }

    fun clearAllFilters() {
        filtersInteractor.clearAllFilters()
        acceptAviable.postValue(false)
        loadCurrentFilters()
    }

    fun loadCurrentFilters() {
        state.postValue(getCurrentFilters())
    }

    fun clearWorkPlace() {
        filtersInteractor.setFilter(SharedFilterNames.COUNTRY, null)
        filtersInteractor.setFilter(SharedFilterNames.AREA, null)
        loadCurrentFilters()
    }
}
