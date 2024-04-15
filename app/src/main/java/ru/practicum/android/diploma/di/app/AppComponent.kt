package ru.practicum.android.diploma.di.app

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.practicum.android.diploma.di.favorites.FavoritesComponent
import ru.practicum.android.diploma.di.filters.CountryComponent
import ru.practicum.android.diploma.di.filters.FiltersMainComponent
import ru.practicum.android.diploma.di.filters.RegionComponent
import ru.practicum.android.diploma.di.filters.WorkPlaceComponent
import ru.practicum.android.diploma.di.filters.IndustryComponent
import ru.practicum.android.diploma.di.general.GeneralComponent
import ru.practicum.android.diploma.di.vacancy.VacancyComponent
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        InteractorModule::class,
        RepositoryModule::class,
        NetworkModule::class,
        SharedPreferencesModule::class,
        DataBaseModule::class,
        UseCaseModule::class
    ]
)
interface AppComponent {

    fun generalComponent(): GeneralComponent

    fun industryComponent(): IndustryComponent

    fun vacancyComponent(): VacancyComponent.VacancyComponentFactory

    fun favoriteslComponent(): FavoritesComponent

    fun workPlaceComponent(): WorkPlaceComponent

    fun countryComponent(): CountryComponent

    fun regionComponent(): RegionComponent
    fun filtersMainComponent(): FiltersMainComponent

    @Component.Factory
    interface AppComponentFactory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}
