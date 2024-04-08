package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.data.dto.Vacancies

interface VacanciesRepository {
    suspend fun search(text: String, page: Int): Vacancies
}
