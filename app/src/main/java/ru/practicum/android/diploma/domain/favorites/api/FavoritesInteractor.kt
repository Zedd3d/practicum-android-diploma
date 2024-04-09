package ru.practicum.android.diploma.domain.favorites.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.favorites.models.FavoriteDbModel

interface FavoritesInteractor {
    fun favoritesVacancies(): Flow<List<FavoriteDbModel>>

    suspend fun insertDbVacanciToFavorite(vacanci: FavoriteDbModel)

}
