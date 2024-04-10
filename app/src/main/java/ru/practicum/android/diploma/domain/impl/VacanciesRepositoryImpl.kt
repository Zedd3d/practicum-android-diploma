package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.asDomain
import ru.practicum.android.diploma.data.dto.Vacancies
import ru.practicum.android.diploma.data.network.HeadHunterService
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyDetail
import javax.inject.Inject

const val PAGINATION_COUNT_PAGES = "20"

class VacanciesRepositoryImpl @Inject constructor(
    private val headHunterService: HeadHunterService
) : VacanciesRepository {

    private var vacanciesList = emptyList<Vacancy>()
    private var found: Int = 0
    override suspend fun search(text: String, page: Int): Vacancies {
        return withContext(Dispatchers.IO) {
            val query = mapOf(
                "text" to text,
                "page" to page.toString(),
                "per_page" to PAGINATION_COUNT_PAGES
            )
            val response = headHunterService.vacancies(query)
            vacanciesList = response.items.asDomain()
            found = response.found
            Vacancies(vacanciesList, found, response.pages)
        }
    }

    override suspend fun searchById(id: String): VacancyDetail {
        return withContext(Dispatchers.IO) {
            headHunterService.getVacancyById(id).asDomain()
        }
    }
}
