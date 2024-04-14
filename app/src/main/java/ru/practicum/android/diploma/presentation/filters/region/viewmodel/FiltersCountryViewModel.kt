package ru.practicum.android.diploma.presentation.filters.region.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.filters.models.FilterValue
import ru.practicum.android.diploma.domain.filters.models.ResponseStateArea
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.sharedpreferences.api.FiltersInteractor
import ru.practicum.android.diploma.presentation.filters.region.state.CountryViewState
import ru.practicum.android.diploma.ui.SingleLiveEvent
import ru.practicum.android.diploma.util.debounceFun
import javax.inject.Inject

class FiltersCountryViewModel @Inject constructor(
    private val filtersInteractor: FiltersInteractor
) : ViewModel() {

    private val state = MutableLiveData<CountryViewState>()

    private val onCountryClickDebounce =
        debounceFun<FilterValue>(CLICK_DELAY, viewModelScope, false) { filterValue ->
            selectCountry.postValue(filterValue)
        }

    private val selectCountry = SingleLiveEvent<FilterValue>()

    companion object {
        private const val CLICK_DELAY = 300L
    }

    init {
        loadAreas()
    }

    fun getSelectCountry(): SingleLiveEvent<FilterValue> = selectCountry

    fun getState(): LiveData<CountryViewState> = state
    fun loadAreas() {
        viewModelScope.launch {
            asyncLoadData()
        }
    }

    suspend fun asyncLoadData() {
        state.postValue(CountryViewState.Loading)

        val response = filtersInteractor.getAreas()

        when (response) {
            is ResponseStateArea.ContentArea -> {
                state.postValue(CountryViewState.Content(response.listAreas))
            }

            is ResponseStateArea.Loading -> state.postValue(CountryViewState.Loading)

            else -> state.postValue(CountryViewState.Error)
        }
    }

    fun selectCountry(area: Area) {
        val filterValue = FilterValue(
            area.id,
            area.parentId,
            area.name,
            area.name
        )
        onCountryClickDebounce(filterValue)
    }
}
