package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.general.models.ResponseState

interface VacanciesRepository {
    suspend fun search(text: String, page: Int): ResponseState

    suspend fun searchById(id: String): ResponseState
}
