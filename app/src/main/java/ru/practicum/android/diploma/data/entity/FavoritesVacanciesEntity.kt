package ru.practicum.android.diploma.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar

@Entity(tableName = "favorites_vacancies_table")
data class FavoritesVacanciesEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val salary: String,
    val experience: String,
    val description: String,
    val employer: String,
    val keySkills: String,
    val area: String,
    val employment: String,
    val inDbTime: Long = Calendar.getInstance().time.time
)
