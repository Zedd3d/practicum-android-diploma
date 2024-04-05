package ru.practicum.android.diploma.data.dto.detail

import java.util.Calendar

data class FavoriteVacancyDto(
    val id: String,
    val name: String,
    val salary: String,
    val experience: String,
    val description: String,
    val employerDto: String,
    val keySkills: String,
    val area: String,
    val employment: String,
    val schedule: String,
    var isFavorite: Boolean = false,
    val inDbTime: Long = Calendar.getInstance().time.time
)
