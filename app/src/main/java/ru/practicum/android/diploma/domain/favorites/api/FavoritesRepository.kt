package ru.practicum.android.diploma.domain.favorites.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.favorites.models.FavoriteDbModel

interface FavoritesRepository {
    fun favoritesVacancies(): Flow<List<FavoriteDbModel>>
    suspend fun deleteDbVacanciFromFavorite(vacID: String)
    suspend fun insertDbVacanciToFavorite(vacanci: FavoriteDbModel)
    suspend fun isFavorite(vacID: String) : Boolean

}
