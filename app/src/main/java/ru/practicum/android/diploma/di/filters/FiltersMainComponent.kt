package ru.practicum.android.diploma.di.filters

import dagger.Subcomponent
import ru.practicum.android.diploma.presentation.filters.main.viewmodel.FiltersMainViewModel

@Subcomponent
interface FiltersMainComponent {

    fun viewModel(): FiltersMainViewModel

}
