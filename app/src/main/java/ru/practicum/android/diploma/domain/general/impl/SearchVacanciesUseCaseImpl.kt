package ru.practicum.android.diploma.domain.general.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.domain.api.VacanciesRepository
import ru.practicum.android.diploma.domain.general.api.SearchVacanciesUseCase
import ru.practicum.android.diploma.domain.general.models.ResponseState
import javax.inject.Inject

class SearchVacanciesUseCaseImpl @Inject constructor(
    private val vacanciesRepository: VacanciesRepository
) : SearchVacanciesUseCase {
    override suspend fun invoke(query: String, page: Int, filters: Map<String, String>): ResponseState {
        return withContext(Dispatchers.IO) {
            vacanciesRepository.search(query, page, filters)
        }
    }
}
