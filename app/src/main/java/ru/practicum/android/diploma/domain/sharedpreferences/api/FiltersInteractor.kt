package ru.practicum.android.diploma.domain.sharedpreferences.api

import ru.practicum.android.diploma.domain.filters.models.FilterValue
import ru.practicum.android.diploma.domain.filters.models.ResponseStateArea

interface FiltersInteractor {
    fun getAllFilters(): Map<String, String>

    fun getFilter(filterName: String): FilterValue?

    fun setFilter(filterName: String, filterValue: FilterValue?)

    suspend fun getAreas(): ResponseStateArea

    suspend fun getAreasByParentId(id: String): ResponseStateArea
}
