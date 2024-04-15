package ru.practicum.android.diploma.di.filters

import dagger.Subcomponent
import ru.practicum.android.diploma.presentation.filters.industry.viewmodel.IndustryViewModel

@Subcomponent
interface IndustryComponent {
    fun viewModel(): IndustryViewModel

}
