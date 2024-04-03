package ru.practicum.android.diploma.data.dto

import ru.practicum.android.diploma.domain.models.Vacancy

data class Vacancies(
    val items: List<Vacancy>,
    val found: Int
)
