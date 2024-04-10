package ru.practicum.android.diploma.domain.favorites.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.favorites.api.FavoritesInteractor
import ru.practicum.android.diploma.domain.favorites.api.FavoritesRepository
import ru.practicum.android.diploma.domain.favorites.models.FavoriteDbModel
import javax.inject.Inject

class FavoritesInteractorImpl
@Inject constructor(private val favoritesRepository: FavoritesRepository) : FavoritesInteractor {
    override fun favoritesVacancies(): Flow<List<FavoriteDbModel>> {
        return favoritesRepository.favoritesVacancies()
    }

    override suspend fun insertDbVacanciToFavorite(vacanci: FavoriteDbModel) {
        favoritesRepository.insertDbVacanciToFavorite(vacanci)
    }

    override suspend fun deleteDbVacanciFromFavorite(vacID: String) {
        favoritesRepository.deleteDbVacanciFromFavorite(vacID)
    }

    override suspend fun isFavorite(vacID: String) : Boolean {
       return favoritesRepository.isFavorite(vacID)
    }
}
