package ru.practicum.android.diploma.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites_vacancies_table")
data class FavoritesVacanciesEntity(
    @PrimaryKey
    val id: String,
    val department: String,
    val name: String,
    val area: String,
    val employer: String,
    val salary: String,
    val type: String
)
// TODO Доделать
