package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.models.Vacancy

interface VacanciesRepository {

    suspend fun search(text: String): List<Vacancy>
}
