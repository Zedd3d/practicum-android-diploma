package ru.practicum.android.diploma.domain.general.api

import ru.practicum.android.diploma.domain.general.models.ResponseState

interface SearchVacanciesByIdUseCase {
    suspend operator fun invoke(id: String): ResponseState

}
