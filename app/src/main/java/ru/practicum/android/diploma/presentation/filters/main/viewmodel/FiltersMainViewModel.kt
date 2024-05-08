package ru.practicum.android.diploma.presentation.filters.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.Currency
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

    private var filterChanged: Boolean = false

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

        val currencyHard = filtersInteractor.getFilter(SharedFilterNames.CURRENCY_HARD)?.valueInt?:0

        return if (
            filtersInteractor.getAllFilters().isEmpty()
        ) {
            FiltersMainViewState.Empty
        } else {
            FiltersMainViewState.Content(
                workPlace = workplace,
                industries = industry,
                salary = salary,
                onlyWithSalary = onlyWithSalary,
                currencyHard = currencyHard,
                filterChanged,
                true

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
        filterChanged = true
        loadCurrentFilters()
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
        filterChanged = true
        loadCurrentFilters()
    }

    fun clearAllFilters() {
        filtersInteractor.clearAllFilters()
        filterChanged = false
        loadCurrentFilters()
    }

    fun loadCurrentFilters(changed: Boolean = false) {
        if (changed) filterChanged = true
        state.postValue(getCurrentFilters())
    }

    fun clearWorkPlace() {
        filterChanged = true
        filtersInteractor.setFilter(SharedFilterNames.COUNTRY, null)
        filtersInteractor.setFilter(SharedFilterNames.AREA, null)
        loadCurrentFilters()
    }

    fun clearIndustry() {
        filterChanged = true
        filtersInteractor.setFilter(SharedFilterNames.INDUSTRY, null)
        loadCurrentFilters()
    }

    fun setCurrencyFilter(currency: Currency) {
        var filterValue: FilterValue? = null

        if (currency.id!=0) {
            filterValue = FilterValue("",
                "", SharedFilterNames.CURRENCY_HARD, currency.name,currency.id
            )
        }
        filtersInteractor.setFilter(SharedFilterNames.CURRENCY_HARD, filterValue)
        filterChanged = true
        loadCurrentFilters()
    }
}
