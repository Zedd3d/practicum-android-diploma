package ru.practicum.android.diploma.domain.favorites.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor() : FavoritesRepository {
    override fun favoritesVacancies(): Flow<List<Vacancy>> {
        TODO("добавить логику загрузки вакансий")
    }

    override suspend fun deleteDbVacanciFromFavorite(vacID: String) {
        TODO("добавить логику удаления из избранного")
    }

    override suspend fun insertDbVacanciToFavorite(vacanci: Vacancy) {
        TODO("добавить логику добавления в избранное")
    }

    override fun setClickedVacanci(vacanci: Vacancy) {
        TODO("добавить логику нажатия на вакансию")
    }
}
