package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.asDomain
import ru.practicum.android.diploma.data.network.HeadHunterService
import ru.practicum.android.diploma.domain.models.Vacancy
import javax.inject.Inject

class VacanciesRepositoryImpl @Inject constructor(
    private val headHunterService: HeadHunterService
): VacanciesRepository {
    override suspend fun search(text: String, page: Int): List<Vacancy> {
        return withContext(Dispatchers.IO) {
            val query = mapOf(
                "text" to text,
                "page" to page.toString(),
                "per_page" to "20"
                )
            headHunterService.vacancies(query).items.asDomain()
        }
    }
}
