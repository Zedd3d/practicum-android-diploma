package ru.practicum.android.diploma.di.filters

import dagger.Subcomponent
import ru.practicum.android.diploma.presentation.filters.industry.viewmodel.FiltersIndustryViewModel

@Subcomponent
interface IndustryComponent {

    fun viewModel(): FiltersIndustryViewModel

}
