package ru.practicum.android.diploma.data.favorites.dto

import java.util.Calendar

data class FavoriteVacancyDto(
    val idDto: String,
    val nameDto: String,
    val salaryDto: String,
    val experienceDto: String,
    val descriptionDto: String,
    val employer: String,
    val keySkillsDto: String,
    val areaDto: String,
    val employmentDto: String,
    val inDbTimeDto: Long = Calendar.getInstance().time.time
)
