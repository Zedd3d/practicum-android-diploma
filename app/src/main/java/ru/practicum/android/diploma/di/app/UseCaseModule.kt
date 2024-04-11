package ru.practicum.android.diploma.di.app

import dagger.Module
import dagger.Provides
import ru.practicum.android.diploma.domain.impl.SearchVacanciesByIdUseCase
import ru.practicum.android.diploma.domain.impl.SearchVacanciesByIdUseCaseImpl
import ru.practicum.android.diploma.domain.impl.SearchVacanciesUseCase
import ru.practicum.android.diploma.domain.impl.SearchVacanciesUseCaseImpl

@Module
object UseCaseModule {

    @Provides
    fun provideSearchVacanciesUseCase(impl: SearchVacanciesUseCaseImpl): SearchVacanciesUseCase = impl

    @Provides
    fun provideSearchVacanciesByIdUseCase(impl: SearchVacanciesByIdUseCaseImpl): SearchVacanciesByIdUseCase = impl

}
