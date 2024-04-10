package ru.practicum.android.diploma.data.favorites.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.converters.VacancyDbConvertor
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.entity.FavoritesVacanciesEntity
import ru.practicum.android.diploma.domain.favorites.api.FavoritesRepository
import ru.practicum.android.diploma.domain.favorites.models.FavoriteDbModel
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase
) : FavoritesRepository {
    override fun favoritesVacancies(): Flow<List<FavoriteDbModel>> = flow {
        val vacancy = appDatabase.favoriteDao().getVacancyFromFavorite()
        emit(convertFromVacancyEntity(vacancy))
    }

    override suspend fun deleteDbVacanciFromFavorite(vacID: String) {
        appDatabase.favoriteDao().deleteVacancyFromFavorite(vacID)
    }

    override suspend fun insertDbVacanciToFavorite(vacanci: FavoriteDbModel) {
        val listVacancy = arrayListOf<FavoriteDbModel>()
        listVacancy.add(vacanci)
        val listFavoriteEntity = convertToVacancyEntity(listVacancy)
        appDatabase.favoriteDao().insertFavoritiesVacancy(listFavoriteEntity)
    }

    override suspend fun isFavorite(vacID: String) : Boolean {
        val isFav = appDatabase.favoriteDao().isVacancyFromFavorite(vacID)
        return isFav.isNotEmpty()
    }

    private fun convertFromVacancyEntity(vacancy: List<FavoritesVacanciesEntity>): List<FavoriteDbModel> {
        return vacancy.map { vac -> VacancyDbConvertor.map(vac) }
    }

    private fun convertToVacancyEntity(listVacancy: List<FavoriteDbModel>): List<FavoritesVacanciesEntity> {
        return listVacancy.map { vac -> VacancyDbConvertor.map(vac) }
    }
}
