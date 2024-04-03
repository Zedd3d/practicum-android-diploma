package ru.practicum.android.diploma.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.practicum.android.diploma.data.dao.FavoritesVacancyDao
import ru.practicum.android.diploma.data.entity.FavoritesVacanciesEntity

@Database(version = 2, entities = [FavoritesVacanciesEntity::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoritesVacancyDao
}
