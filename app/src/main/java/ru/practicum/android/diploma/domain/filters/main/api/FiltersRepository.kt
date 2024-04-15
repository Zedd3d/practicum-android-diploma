package ru.practicum.android.diploma.domain.filters.main.api

import ru.practicum.android.diploma.domain.filters.models.ResponseStateArea

interface FiltersRepository {
    suspend fun getAreas(): ResponseStateArea
    suspend fun getAreasById(id: String): ResponseStateArea
}
