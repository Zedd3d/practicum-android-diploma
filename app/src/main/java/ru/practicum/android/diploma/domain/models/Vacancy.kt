package ru.practicum.android.diploma.domain.models

data class Vacancy(
    val id: String,
    val img: String?,
    val employer: String,
    val name: String,
    val salary: String?,
    val area: String
)
