package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.data.dto.Vacancies

interface SearchVacanciesUseCase {

    suspend operator fun invoke(query: String, page: Int): Vacancies

}
