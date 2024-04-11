package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.general.models.ResponseState

interface SearchVacanciesUseCase {

    suspend operator fun invoke(query: String, page: Int): ResponseState

}
