package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.data.dto.Vacancies
import ru.practicum.android.diploma.domain.models.VacancyDetail

interface VacanciesRepository {
    suspend fun search(text: String, page: Int): Vacancies

    suspend fun searchById(id: String): VacancyDetail
}
