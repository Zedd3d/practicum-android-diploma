package ru.practicum.android.diploma.domain.favorites.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.favorites.impl.FavoritesRepository
import ru.practicum.android.diploma.domain.models.Vacancy
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor () : FavoritesRepository {
    override fun favoritesVacancies(): Flow<List<Vacancy>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteDbVacanciFromFavorite(vacID: String) {
        TODO("Not yet implemented")
    }

    override suspend fun insertDbVacanciToFavorite(vacanci: Vacancy) {
        TODO("Not yet implemented")
    }

    override fun setClickedVacanci(vacanci: Vacancy) {
        TODO("Not yet implemented")
    }
}
