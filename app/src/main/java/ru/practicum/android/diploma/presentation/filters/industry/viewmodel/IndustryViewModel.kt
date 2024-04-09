package ru.practicum.android.diploma.presentation.filters.industry.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.data.Constants.SUCCESS_RESULT_CODE
import ru.practicum.android.diploma.data.network.Resource
import ru.practicum.android.diploma.domain.filters.industry.models.Industry
import ru.practicum.android.diploma.domain.filters.industry.models.SubIndustry
import ru.practicum.android.diploma.presentation.filters.FIlterInteractor
import ru.practicum.android.diploma.presentation.filters.industry.fragment.IndustriesAdapterItem
import ru.practicum.android.diploma.presentation.filters.industry.state.FiltersIndustriesState
import javax.inject.Inject

class FiltersIndustryViewModel @Inject constructor(private val filterInteractor: FIlterInteractor) : ViewModel() {
    private val _industriesState = MutableLiveData<FiltersIndustriesState>()
    val industriesState: LiveData<FiltersIndustriesState> = _industriesState
    private var currentIndustriesList = ArrayList<SubIndustry>()

    fun getIndustries() {
        _industriesState.value = FiltersIndustriesState.Loading
        viewModelScope.launch {
            filterInteractor.getIndustries().collect { industry ->
                loadIndustries(industry)
            }
        }
    }

    private fun loadIndustries(industry: Resource<List<Industry>>) {
        if (industry.code == SUCCESS_RESULT_CODE) {
            if (industry.data != null) {
                currentIndustriesList.clear()
                currentIndustriesList.addAll(sortIndustries(industry.data))
                _industriesState.value =
                    FiltersIndustriesState.Success(currentIndustriesList.map { IndustriesAdapterItem(it) })
            } else {
                _industriesState.value = FiltersIndustriesState.Empty
            }
        } else {
            _industriesState.value = FiltersIndustriesState.Error
        }
    }

    private fun sortIndustries(industriesList: List<Industry>): List<SubIndustry> {
        val sortedSubIndustriesList: MutableList<SubIndustry> = mutableListOf()
        for (industry in industriesList) {
            for (subindustry in industry.industries) {
                sortedSubIndustriesList.add(
                    SubIndustry(
                        id = subindustry.id,
                        name = subindustry.name
                    )
                )
            }
            sortedSubIndustriesList.add(
                SubIndustry(
                    id = industry.id,
                    name = industry.name
                )
            )
        }

        return sortedSubIndustriesList.sortedBy { it.name }
    }

    fun filterIndustries(editText: String) {
        if (editText.isNotEmpty()) {
            val filteredList = currentIndustriesList.filter {
                it.name.contains(editText, ignoreCase = true)
            }
            if (filteredList.isNotEmpty()) {
                _industriesState.value = FiltersIndustriesState.Success(filteredList.map { IndustriesAdapterItem(it) })
            } else {
                _industriesState.value = FiltersIndustriesState.Empty
            }
        } else {
            _industriesState.value =
                FiltersIndustriesState.Success(currentIndustriesList.map { IndustriesAdapterItem(it) })
        }
    }
}
