package ru.practicum.android.diploma.di.app

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.practicum.android.diploma.di.favorites.FavoritesComponent
import ru.practicum.android.diploma.di.general.GeneralComponent
import ru.practicum.android.diploma.di.vacancy.VacancyComponent
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        RepositoryModule::class,
        NetworkModule::class,
        DataBaseModule::class
    ]
)
interface AppComponent {

    fun generalComponent(): GeneralComponent

    fun vacancyComponent(): VacancyComponent.VacancyComponentFactory

    fun favoriteslComponent(): FavoritesComponent

    @Component.Factory
    interface AppComponentFactory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}
