package ru.practicum.android.diploma.domain.sharedpreferences.api

import ru.practicum.android.diploma.domain.filters.models.FilterValue

interface SharedPreferencesInteractor {
    fun getAllFilters(): HashMap<String, String>

    fun getFilter(filterName: String): FilterValue?

    fun setFilter(filterName: String, filterValue: FilterValue)
}
