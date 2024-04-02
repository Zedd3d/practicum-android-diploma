package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.asDomain
import ru.practicum.android.diploma.data.dto.Vacancies
import ru.practicum.android.diploma.data.network.HeadHunterService
import ru.practicum.android.diploma.domain.models.Vacancy
import javax.inject.Inject

class VacanciesRepositoryImpl @Inject constructor(
    private val headHunterService: HeadHunterService
): VacanciesRepository {

    private lateinit var vacanciesList: List<Vacancy>
    private var found: Int = 0
    override suspend fun search(text: String, page: Int): Vacancies {
         withContext(Dispatchers.IO) {
            val query = mapOf(
                "text" to text,
                "page" to page.toString(),
                "per_page" to "20"
                )
             val response = headHunterService.vacancies(query)
             vacanciesList = response.items.asDomain()
             found = response.found
        }
        return Vacancies(vacanciesList, found)
    }
}
