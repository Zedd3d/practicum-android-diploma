package ru.practicum.android.diploma.di.app

import dagger.Module
import dagger.Provides
import ru.practicum.android.diploma.presentation.filters.FIlterInteractor

@Module
object InteractorModule {
    @Provides
    fun providesIndustryInteractor(interactor: FIlterInteractor): FIlterInteractor = interactor
}
