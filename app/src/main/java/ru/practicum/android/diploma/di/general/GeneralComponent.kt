package ru.practicum.android.diploma.di.general

import dagger.Subcomponent
import ru.practicum.android.diploma.presentation.general.GeneralViewModel

@Subcomponent
interface GeneralComponent {

    fun viewModel(): GeneralViewModel

}
