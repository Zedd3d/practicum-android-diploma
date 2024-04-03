package ru.practicum.android.diploma.di.app

import dagger.Module
import dagger.Provides
import ru.practicum.android.diploma.domain.favorites.impl.FavoritesRepository
import ru.practicum.android.diploma.domain.favorites.impl.FavoritesRepositoryImpl
import ru.practicum.android.diploma.domain.impl.VacanciesRepository
import ru.practicum.android.diploma.domain.impl.VacanciesRepositoryImpl

@Module
object RepositoryModule {

    @Provides
    fun providesVacanciesRepository(impl: VacanciesRepositoryImpl): VacanciesRepository = impl
    @Provides
    fun providesFavoritiesRepository(impl: FavoritesRepositoryImpl): FavoritesRepository = impl
}
