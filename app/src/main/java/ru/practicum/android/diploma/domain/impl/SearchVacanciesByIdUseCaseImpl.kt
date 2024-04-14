package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.domain.general.models.ResponseState
import javax.inject.Inject

class SearchVacanciesByIdUseCaseImpl @Inject constructor(
    private val vacanciesRepository: VacanciesRepository
) : SearchVacanciesByIdUseCase {
    override suspend fun invoke(id: String): ResponseState {
        return withContext(Dispatchers.IO) {
            vacanciesRepository.searchById(id)
        }
    }
}
