package ru.practicum.android.diploma.domain.favorites.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.dto.detail.FavoriteVacancyDto

interface FavoritesInteractor {
    fun favoritesVacancies(): Flow<List<FavoriteVacancyDto>>

}
