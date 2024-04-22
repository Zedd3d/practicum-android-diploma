package ru.practicum.android.diploma.domain.filters.models

import ru.practicum.android.diploma.domain.models.Area

sealed class ResponseStateArea {
    data object Empty : ResponseStateArea()
    data object NetworkError : ResponseStateArea()
    data object ServerError : ResponseStateArea()
    data object Loading : ResponseStateArea()
    data class ContentArea(val listAreas: List<Area>) : ResponseStateArea()
}
