package ru.practicum.android.diploma.di.app

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import ru.practicum.android.diploma.data.filters.SharedPreferencesRepositoryImpl
import ru.practicum.android.diploma.domain.favorites.impl.FavoritesRepository
import ru.practicum.android.diploma.domain.favorites.impl.FavoritesRepositoryImpl
import ru.practicum.android.diploma.domain.impl.VacanciesRepository
import ru.practicum.android.diploma.domain.impl.VacanciesRepositoryImpl
import ru.practicum.android.diploma.domain.sharedpreferences.api.SharedPreferencesRepository

@Module
object RepositoryModule {

    @Provides
    fun providesVacanciesRepository(impl: VacanciesRepositoryImpl): VacanciesRepository = impl

    @Provides
    fun providesFavoritiesRepository(impl: FavoritesRepositoryImpl): FavoritesRepository = impl

    @Provides
    fun providesSharedPreferencesRepository(context: Context): SharedPreferencesRepository {
        return SharedPreferencesRepositoryImpl(
            Gson(),
            context.getSharedPreferences(
                "hh_filters",
                Application.MODE_PRIVATE
            )
        )
    }

}
