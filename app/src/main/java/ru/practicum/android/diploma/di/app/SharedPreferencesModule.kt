package ru.practicum.android.diploma.di.app

import dagger.Module
import dagger.Provides
import ru.practicum.android.diploma.domain.sharedpreferences.api.SharedPreferencesInteractor
import ru.practicum.android.diploma.domain.sharedpreferences.impl.SharedPreferencesInteractorImpl

@Module
object SharedPreferencesModule {
    @Provides
    fun providesSharedPreferencesInteractor(
        spinteractor: SharedPreferencesInteractorImpl
    ): SharedPreferencesInteractor = spinteractor

}
