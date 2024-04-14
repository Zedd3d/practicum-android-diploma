package ru.practicum.android.diploma.domain.filters

import ru.practicum.android.diploma.domain.filters.main.api.FiltersRepository
import ru.practicum.android.diploma.domain.filters.models.FilterValue
import ru.practicum.android.diploma.domain.filters.models.ResponseStateArea
import ru.practicum.android.diploma.domain.sharedpreferences.api.FiltersInteractor
import ru.practicum.android.diploma.domain.sharedpreferences.api.SharedPreferencesRepository
import ru.practicum.android.diploma.domain.sharedpreferences.model.SharedFilterNames
import javax.inject.Inject

class FiltersInteractorImpl @Inject constructor(
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    private val filtersRepository: FiltersRepository
) : FiltersInteractor {

    override fun getAllFilters(): Map<String, String> {
        return sharedPreferencesRepository.getAllFilters()
    }

    override fun getFilter(filterName: String): FilterValue? {
        return sharedPreferencesRepository.getFilter(filterName)
    }

    override fun setFilter(filterName: String, filterValue: FilterValue?) {
        sharedPreferencesRepository.setFilter(filterName, filterValue)
    }

    override suspend fun getAreas(): ResponseStateArea {
        return filtersRepository.getAreas()
    }

    override suspend fun getAreasByParentId(id: String): ResponseStateArea {
        return filtersRepository.getAreasById(id)
    }

    override fun clearAllFilters() {
        val allFilters = sharedPreferencesRepository.getAllFilters()
        allFilters.forEach {
            sharedPreferencesRepository.setFilter(it.key, null)
        }
        sharedPreferencesRepository.setFilter(SharedFilterNames.COUNTRY, null)
    }


}
