package ru.practicum.android.diploma.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.practicum.android.diploma.data.dao.FavoritesVacancyDao
import ru.practicum.android.diploma.data.favorites.entity.FavoritesVacanciesEntity

@Database(version = 6, entities = [FavoritesVacanciesEntity::class], exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoritesVacancyDao
}
