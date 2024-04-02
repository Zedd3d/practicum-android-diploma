package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy

interface FavoritesRepository {
    fun favoritesVacancies() : Flow<List<Vacancy>>
    suspend fun deleteDbVacanciFromFavorite(vacID: String)
    suspend fun insertDbVacanciToFavorite(vacanci: Vacancy)
    fun setClickedVacanci (vacanci: Vacancy)
}
