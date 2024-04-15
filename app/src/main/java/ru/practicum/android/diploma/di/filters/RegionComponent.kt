package ru.practicum.android.diploma.di.filters

import dagger.Subcomponent
import ru.practicum.android.diploma.presentation.filters.region.viewmodel.FiltersRegionViewModel

@Subcomponent
interface RegionComponent {
    fun viewModel(): FiltersRegionViewModel

}
