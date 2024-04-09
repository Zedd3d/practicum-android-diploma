package ru.practicum.android.diploma.domain.sharedpreferences.impl

import android.content.SharedPreferences
import ru.practicum.android.diploma.domain.filters.models.FilterValue
import ru.practicum.android.diploma.domain.sharedpreferences.api.SharedPreferencesInteractor
import ru.practicum.android.diploma.domain.sharedpreferences.api.SharedPreferencesRepository
import javax.inject.Inject

class SharedPreferencesInteractorImpl @Inject constructor(
    private val sharedPreferencesRepository: SharedPreferencesRepository
) : SharedPreferencesInteractor {
    override fun setSharedPreferencesChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        sharedPreferencesRepository.setSharedPreferencesChangeListener(listener)
    }

    override fun getAllFilters(): HashMap<String, String> {
        return sharedPreferencesRepository.getAllFilters()
    }

    override fun getAreaFilter(): FilterValue? {
        return sharedPreferencesRepository.getAreaFilter()
    }

    override fun getCountryFilter(): FilterValue? {
        return sharedPreferencesRepository.getCountryFilter()
    }

    override fun getIndustryFilter(): FilterValue? {
        return sharedPreferencesRepository.getIndustryFilter()
    }

    override fun getSalaryFilter(): FilterValue? {
        return sharedPreferencesRepository.getSalaryFilter()
    }

    override fun getOnlyWithSalaryFilter(): FilterValue? {
        return sharedPreferencesRepository.getOnlyWithSalaryFilter()
    }

    override fun setAreaFilter(filterValue: FilterValue) {
        sharedPreferencesRepository.setAreaFilter(filterValue)
    }

    override fun setCountryFilter(filterValue: FilterValue) {
        sharedPreferencesRepository.setCountryFilter(filterValue)
    }

    override fun setIndustryFilter(filterValue: FilterValue) {
        sharedPreferencesRepository.setIndustryFilter(filterValue)
    }

    override fun setSalaryFilter(filterValue: FilterValue) {
        sharedPreferencesRepository.setSalaryFilter(filterValue)
    }

    override fun setOnlyWithSalaryFilter(filterValue: FilterValue) {
        sharedPreferencesRepository.setOnlyWithSalsryFilter(filterValue)
    }
}
