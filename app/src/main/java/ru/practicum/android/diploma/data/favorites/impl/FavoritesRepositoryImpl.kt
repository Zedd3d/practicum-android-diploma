package ru.practicum.android.diploma.data.favorites.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.converters.VacancyDbConvertor
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.dto.detail.FavoriteVacancyDto
import ru.practicum.android.diploma.data.entity.FavoritesVacanciesEntity
import ru.practicum.android.diploma.domain.favorites.api.FavoritesRepository
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase,
    private val vacancyDbConvertor: VacancyDbConvertor
) : FavoritesRepository {
    override fun favoritesVacancies(): Flow<List<FavoriteVacancyDto>> = flow {
        val vacancy = appDatabase.favoriteDao().getVacancyFromFavorite()
        emit(convertFromVacancyEntity(vacancy))
    }

    override suspend fun deleteDbVacanciFromFavorite(vacID: String) {
        appDatabase.favoriteDao().updateVacancyFromFavorite(vacID)
    }

    override suspend fun insertDbVacanciToFavorite(vacanci: FavoriteVacancyDto) {
        vacanci.isFavorite = true
        val listVacancy = arrayListOf<FavoriteVacancyDto>()
        listVacancy.add(vacanci)
        val listFavoriteEntity = convertToVacancyEntity(listVacancy)
        appDatabase.favoriteDao().insertFavoritiesVacancy(listFavoriteEntity)
    }

    private fun convertFromVacancyEntity(vacancy: List<FavoritesVacanciesEntity>): List<FavoriteVacancyDto> {
        return vacancy.map { vac -> vacancyDbConvertor.map(vac) }
    }
    private fun convertToVacancyEntity(listVacancy: List<FavoriteVacancyDto>): List<FavoritesVacanciesEntity> {
        return listVacancy.map { vac -> vacancyDbConvertor.map(vac) }
    }
}
