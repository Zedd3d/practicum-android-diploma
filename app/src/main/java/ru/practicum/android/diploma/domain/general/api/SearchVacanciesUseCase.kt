package ru.practicum.android.diploma.domain.general.api

import ru.practicum.android.diploma.domain.general.models.ResponseState

interface SearchVacanciesUseCase {

    suspend operator fun invoke(query: String, page: Int, filters: Map<String, String>): ResponseState

}
