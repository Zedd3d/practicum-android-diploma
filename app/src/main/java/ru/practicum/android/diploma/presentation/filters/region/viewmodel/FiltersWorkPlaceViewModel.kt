package ru.practicum.android.diploma.presentation.filters.region.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.filters.models.FilterValue
import ru.practicum.android.diploma.domain.sharedpreferences.api.FiltersInteractor
import ru.practicum.android.diploma.domain.sharedpreferences.model.SharedFilterNames
import ru.practicum.android.diploma.presentation.filters.region.state.FiltersWorkPlaceViewState
import ru.practicum.android.diploma.ui.SingleLiveEvent
import javax.inject.Inject

class FiltersWorkPlaceViewModel @Inject constructor(
    private val filtersInteractor: FiltersInteractor
) : ViewModel() {

    private var currentFilterCountry: FilterValue? = null
    private var currentFilterRegion: FilterValue? = null
    private val state = MutableLiveData<FiltersWorkPlaceViewState>()

    private val selectRegion = SingleLiveEvent<String>()

    init {
        currentFilterCountry = filtersInteractor.getFilter(SharedFilterNames.COUNTRY)
        currentFilterRegion = filtersInteractor.getFilter(SharedFilterNames.AREA)
        loadValues(false)
    }

    fun getState(): LiveData<FiltersWorkPlaceViewState> = state

    fun getSelectRegion(): SingleLiveEvent<String> = selectRegion

    fun setFilterCountry(filterValue: FilterValue?) {
        currentFilterCountry = filterValue
        currentFilterCountry?.id?.let { parId ->
            currentFilterRegion?.parentId?.let {
                if (it.isNotEmpty()
                    && parId.isNotEmpty()
                    && it != parId
                ) {
                    setFilterRegion(null)
                }
            }
        }

        filtersInteractor.setFilter(SharedFilterNames.COUNTRY, filterValue)
        loadValues(true)
    }

    fun setFilterRegion(filterValue: FilterValue?) {
        currentFilterRegion = filterValue
        filtersInteractor.setFilter(SharedFilterNames.AREA, filterValue)
        loadValues(true)
    }

    private fun loadValues(valuesChanged: Boolean) {
        if (currentFilterCountry == null
            && currentFilterRegion == null
        ) {
            state.postValue(FiltersWorkPlaceViewState.Empty)
        } else {
            state.postValue(
                FiltersWorkPlaceViewState.Content(
                    country = currentFilterCountry?.valueString ?: "",
                    region = currentFilterRegion?.valueString ?: "",
                    filterChanged = valuesChanged
                )
            )
        }
    }

    fun saveFilters() {
        filtersInteractor.setFilter(SharedFilterNames.COUNTRY, currentFilterCountry)
        filtersInteractor.setFilter(SharedFilterNames.AREA, currentFilterRegion)
    }

    fun selectRegion() {
        selectRegion.postValue(currentFilterCountry?.id ?: "")
    }

    fun clearCountry() {
        currentFilterCountry = null
        saveFilters()
        loadValues(true)
    }

    fun clearRegion() {
        currentFilterRegion = null
        saveFilters()
        loadValues(true)
    }
}
