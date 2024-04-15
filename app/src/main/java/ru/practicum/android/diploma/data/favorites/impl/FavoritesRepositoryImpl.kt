package ru.practicum.android.diploma.data.favorites.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.converters.VacancyDbConvertor
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.favorites.entity.FavoritesVacanciesEntity
import ru.practicum.android.diploma.domain.favorites.api.FavoritesRepository
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyDetail
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase
) : FavoritesRepository {
    override fun favoritesVacancies(): Flow<List<Vacancy>> = flow {
        val vacancyList = appDatabase.favoriteDao().getVacancyFromFavorite()
        emit(convertFromVacancyEntity(vacancyList))
    }

    override suspend fun deleteDbVacanciFromFavorite(vacID: String) {
        appDatabase.favoriteDao().deleteVacancyFromFavorite(vacID)
    }

    override suspend fun insertDbVacanciToFavorite(vacancyDetail: VacancyDetail) {
        appDatabase.favoriteDao().insertFavoritiesVacancy(convertToVacancyEntity(vacancyDetail))
    }

    override suspend fun isFavorite(vacID: String): Boolean {
        return appDatabase.favoriteDao().elementById(vacID).isNotEmpty()
    }

    override suspend fun loadFavoriteVacancy(vacID: String): VacancyDetail? {
        val list = appDatabase.favoriteDao().elementById(vacID)
        return if (list.isEmpty()) {
            null
        } else {
            VacancyDbConvertor.mapToVacancyDetail(list.first())
        }
    }

    private fun convertFromVacancyEntity(vacancyList: List<FavoritesVacanciesEntity>): List<Vacancy> {
        return vacancyList.map { vac -> VacancyDbConvertor.mapToVacancy(vac) }
    }

    private fun convertToVacancyEntity(vacancyDetail: VacancyDetail): FavoritesVacanciesEntity {
        return VacancyDbConvertor.map(vacancyDetail)
    }
}
