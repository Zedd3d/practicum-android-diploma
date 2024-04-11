package ru.practicum.android.diploma.data.dto

import ru.practicum.android.diploma.domain.general.models.ResponseState

data class Vacancies(
    val state: ResponseState,
    val found: Int,
    val pages: Int
)
