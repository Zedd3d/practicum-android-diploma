package ru.practicum.android.diploma.domain.favorites.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor() : FavoritesRepository {
    override fun favoritesVacancies(): Flow<List<Vacancy>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteDbVacanciFromFavorite(vacID: String) {

    }

    override suspend fun insertDbVacanciToFavorite(vacanci: Vacancy) {

    }

    override fun setClickedVacanci(vacanci: Vacancy) {

    }
}
