package ru.practicum.android.diploma.domain.sharedpreferences.api

import android.content.SharedPreferences
import ru.practicum.android.diploma.domain.filters.models.FilterValue

interface SharedPreferencesInteractor {
    fun setSharedPreferencesChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener)

    fun getAllFilters(): HashMap<String, String>

    fun getAreaFilter(): FilterValue?

    fun getCountryFilter(): FilterValue?

    fun getIndustryFilter(): FilterValue?

    fun getSalaryFilter(): FilterValue?

    fun getOnlyWithSalaryFilter(): FilterValue?

    fun setAreaFilter(filterValue: FilterValue)

    fun setCountryFilter(filterValue: FilterValue)

    fun setIndustryFilter(filterValue: FilterValue)

    fun setSalaryFilter(filterValue: FilterValue)

    fun setOnlyWithSalaryFilter(filterValue: FilterValue)
}
