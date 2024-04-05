package ru.practicum.android.diploma.domain.favorites.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.dto.detail.FavoriteVacancyDto
import ru.practicum.android.diploma.domain.models.Vacancy

class FavoritesInteractorImpl(private val favoritesRepository: FavoritesRepository) : FavoritesInteractor {
    override fun favoritesVacancies(): Flow<List<FavoriteVacancyDto>> {
        return favoritesRepository.favoritesVacancies()
    }

}
