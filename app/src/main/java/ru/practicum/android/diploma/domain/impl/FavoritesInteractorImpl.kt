package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy

class FavoritesInteractorImpl(private val favoritesRepository: FavoritesRepository) : FavoritesInteractor {
    override fun favoritesVacancies(): Flow<List<Vacancy>> {
        return favoritesRepository.favoritesVacancies()
    }

    override fun setClickedVacanci(vacanci: Vacancy) {
        favoritesRepository.setClickedVacanci(vacanci)
    }
}
