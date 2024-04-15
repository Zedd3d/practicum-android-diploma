package ru.practicum.android.diploma.domain.sharedpreferences.impl

import ru.practicum.android.diploma.domain.filters.models.FilterValue
import ru.practicum.android.diploma.domain.sharedpreferences.api.SharedPreferencesInteractor
import ru.practicum.android.diploma.domain.sharedpreferences.api.SharedPreferencesRepository
import javax.inject.Inject

class SharedPreferencesInteractorImpl @Inject constructor(
    private val sharedPreferencesRepository: SharedPreferencesRepository
) : SharedPreferencesInteractor {

    override fun getAllFilters(): HashMap<String, String> {
        return sharedPreferencesRepository.getAllFilters()
    }

    override fun getFilter(filterName: String): FilterValue? {
        return sharedPreferencesRepository.getFilter(filterName)
    }

    override fun setFilter(filterName: String, filterValue: FilterValue) {
        sharedPreferencesRepository.setFilter(filterName, filterValue)
    }
}
