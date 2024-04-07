package ru.practicum.android.diploma.data.filters

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.practicum.android.diploma.domain.filters.models.FilterValue
import ru.practicum.android.diploma.domain.sharedpreferences.api.SharedPreferencesRepository

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

    private fun setFilter(filterName: String, filterValue: FilterValue) {
        val json = gson.toJson(filterValue)
        sharedPref.edit().putString(filterName, json).apply()
    }

    private fun getFilter(filterName: String): FilterValue? {
        return sharedPref.getString(filterName, null).let {
            if (it.isNullOrEmpty()) return null
            getFilterValue(it)
        }
    }

    override fun getAreaFilter(): FilterValue? {
        return getFilter(AREA)

    }

    override fun getCountryFilter(): FilterValue? {
        return getFilter(COUNTRY)
    }

    override fun getIndustryFilter(): FilterValue? {
        return getFilter(INDUSTRY)
    }

    override fun getSalaryFilter(): FilterValue? {
        return getFilter(SALARY)
    }

    override fun getOnlyWithSalaryFilter(): FilterValue? {
        return getFilter(ONLY_WITH_SALARY)
    }

    override fun setAreaFilter(filterValue: FilterValue) {
        setFilter(AREA, filterValue)
    }

    override fun setCountryFilter(filterValue: FilterValue) {
        setFilter(COUNTRY, filterValue)
    }

    override fun setIndustryFilter(filterValue: FilterValue) {
        setFilter(INDUSTRY, filterValue)
    }

    override fun setSalaryFilter(filterValue: FilterValue) {
        setFilter(SALARY, filterValue)
    }

    override fun setOnlyWithSalsryFilter(filterValue: FilterValue) {
        setFilter(ONLY_WITH_SALARY, filterValue)
    }

}
