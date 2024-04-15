package ru.practicum.android.diploma.domain.general.models

import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyDetail

sealed class ResponseState {

    data object Start : ResponseState()
    data object Empty : ResponseState()
    data class ContentVacanciesList(
        val listVacancy: List<Vacancy>,
        val found: Int,
        val pages: Int,
        val isWithFilters: Boolean
    ) : ResponseState()

    data class ContentVacancyDetail(val vacancyDetail: VacancyDetail) : ResponseState()
    data class NetworkError(val isPagination: Boolean) : ResponseState()
    data object ServerError : ResponseState()
    data class Loading(val isPagination: Boolean) : ResponseState()

}
