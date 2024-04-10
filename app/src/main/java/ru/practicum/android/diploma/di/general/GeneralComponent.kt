package ru.practicum.android.diploma.di.general

import dagger.Subcomponent
import ru.practicum.android.diploma.presentation.general.viewmodel.GeneralViewModel

@Subcomponent
interface GeneralComponent {

    fun viewModel(): GeneralViewModel
}
