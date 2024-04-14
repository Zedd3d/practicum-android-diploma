package ru.practicum.android.diploma.presentation.filters.region.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.filters.models.FilterValue
import ru.practicum.android.diploma.domain.sharedpreferences.api.FiltersInteractor
import ru.practicum.android.diploma.domain.sharedpreferences.model.SharedFilterNames
import ru.practicum.android.diploma.presentation.filters.region.state.FiltersWorkPlaceViewState
import javax.inject.Inject

class FiltersWorkPlaceViewModel @Inject constructor(
    private val filtersInteractor: FiltersInteractor
) : ViewModel() {

    private var currentFilterCountry: FilterValue? = null
    private var currentFilterRegion: FilterValue? = null
    private val filterChanged = MutableLiveData<Boolean>()
    private val state = MutableLiveData<FiltersWorkPlaceViewState>()

    init {
        currentFilterCountry = filtersInteractor.getFilter(SharedFilterNames.COUNTRY)
        currentFilterRegion = filtersInteractor.getFilter(SharedFilterNames.AREA)
    }

    fun getState(): LiveData<FiltersWorkPlaceViewState> = state

    fun getFilterChanged(): LiveData<Boolean> = filterChanged

    fun valuesChanged() {
        filterChanged.postValue(true)
    }

    fun setFilterCountry(filterValue: FilterValue?) {
        filtersInteractor.setFilter(SharedFilterNames.COUNTRY, filterValue)
        setNewValues()
        valuesChanged()
    }

    private fun setNewValues() {
        if (currentFilterCountry == null
            && currentFilterRegion == null
        ) {
            state.postValue(FiltersWorkPlaceViewState.Empty)
        } else {
            state.postValue(
                FiltersWorkPlaceViewState.Content(
                    country = currentFilterCountry?.valueString ?: "",
                    region = currentFilterRegion?.valueString ?: ""
                )
            )
        }
    }

    fun saveFilters() {
        filtersInteractor.setFilter(SharedFilterNames.COUNTRY, currentFilterCountry)
        filtersInteractor.setFilter(SharedFilterNames.AREA, currentFilterRegion)
    }
}
