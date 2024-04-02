package ru.practicum.android.diploma.data.dto

data class VacanciesResponse (
    val items: List<VacancyDto>,
    val found: Int
)
