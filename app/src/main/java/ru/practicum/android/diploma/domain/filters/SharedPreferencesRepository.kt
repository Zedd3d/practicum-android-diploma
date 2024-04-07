package ru.practicum.android.diploma.domain.filters

import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import ru.practicum.android.diploma.domain.filters.models.FilterValue

interface SharedPreferencesRepository {
    fun setSharedPreferencesChangeListener(listener: OnSharedPreferenceChangeListener)

    fun getAllFilters(): HashMap<String, String>

    fun putFilter(filterName: String, filterValue: FilterValue)
}
