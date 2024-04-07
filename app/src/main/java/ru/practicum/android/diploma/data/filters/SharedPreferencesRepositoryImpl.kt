package ru.practicum.android.diploma.data.filters

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.practicum.android.diploma.domain.filters.SharedPreferencesRepository
import ru.practicum.android.diploma.domain.filters.models.FilterValue


class SharedPreferencesRepositoryImpl(
    private val gson: Gson,
    private val sharedPref: SharedPreferences
) : SharedPreferencesRepository {

    companion object {
        private const val AREA = "area"
        private const val COUNTRY = "country"
        private const val INDUSTRY = "industry"
        private const val SALARY = "salary"
        private const val ONLY_WITH_SALARY = "only_with_salary"
    }

    override fun setSharedPreferencesChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        sharedPref.registerOnSharedPreferenceChangeListener(listener)
    }

    private fun getFilterValue(json: String): FilterValue {
        return gson.fromJson<FilterValue>(
            json,
            object : TypeToken<FilterValue>() {}.type
        )
    }

    override fun getAllFilters(): HashMap<String, String> {

        val result = HashMap<String, String>()

        val areaFilter = sharedPref.getString(AREA, null)
        if (!areaFilter.isNullOrEmpty()) {
            result.put(AREA, getFilterValue(areaFilter).id)
        }

        val industryFilter = sharedPref.getString(INDUSTRY, null)
        if (!industryFilter.isNullOrEmpty()) {
            result.put(INDUSTRY, getFilterValue(industryFilter).id)
        }

        val salaryFilter = sharedPref.getString(SALARY, null)
        if (!salaryFilter.isNullOrEmpty()) {
            result.put(SALARY, getFilterValue(salaryFilter).valueString)
        }

        val inlyWithSalaryFilter = sharedPref.getString(ONLY_WITH_SALARY, null)
        if (!inlyWithSalaryFilter.isNullOrEmpty()) {
            result.put(ONLY_WITH_SALARY, getFilterValue(inlyWithSalaryFilter).valueString)
        }

        return result
    }

    override fun putFilter(filterName: String, filterValue: FilterValue) {
        val json = gson.toJson(filterValue)
        sharedPref.edit().putString(filterName, json).apply()
    }

}
