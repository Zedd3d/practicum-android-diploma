package ru.practicum.android.diploma.domain.favorites.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.dto.detail.FavoriteVacancyDto
import ru.practicum.android.diploma.domain.models.Vacancy

interface FavoritesInteractor {
    fun favoritesVacancies(): Flow<List<FavoriteVacancyDto>>

}
