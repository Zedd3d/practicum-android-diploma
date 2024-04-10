package ru.practicum.android.diploma.domain.favorites.models

import java.util.Calendar

data class FavoriteDbModel(
    val idDto: String,
    val nameDto: String,
    val salaryDto: String,
    val experienceDto: String,
    val descriptionDto: String,
    val employerDto: String,
    val keySkillsDto: String,
    val areaDto: String,
    val employmentDto: String,
    val inDbTimeDto: Long = Calendar.getInstance().time.time
)
