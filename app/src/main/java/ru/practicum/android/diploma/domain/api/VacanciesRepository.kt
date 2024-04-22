package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.general.models.ResponseState

interface VacanciesRepository {
    suspend fun search(text: String, page: Int, filters: Map<String, String>): ResponseState

    suspend fun searchById(id: String): ResponseState
}
