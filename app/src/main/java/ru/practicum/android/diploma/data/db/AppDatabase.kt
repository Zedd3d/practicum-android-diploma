package ru.practicum.android.diploma.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.practicum.android.diploma.data.dao.FavoritesVacancyDao
import ru.practicum.android.diploma.data.favorites.entity.FavoritesVacanciesEntity
import ru.practicum.android.diploma.domain.filters.industry.models.Industry
import ru.practicum.android.diploma.data.dao.IndustryDao

@Database(version = 6, entities = [FavoritesVacanciesEntity::class, Industry::class], exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoritesVacancyDao

    abstract fun IndustryDao(): IndustryDao
}
