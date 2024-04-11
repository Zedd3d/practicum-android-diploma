package ru.practicum.android.diploma.data.general.impl

import ru.practicum.android.diploma.data.asDomain
import ru.practicum.android.diploma.data.dto.VacanciesResponse
import ru.practicum.android.diploma.data.dto.detail.VacancyDetailDto
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.domain.general.models.ResponseState
import ru.practicum.android.diploma.domain.impl.VacanciesRepository
import javax.inject.Inject

const val PAGINATION_COUNT_PAGES = "20"

class VacanciesRepositoryImpl @Inject constructor(
    private val retrofitNetworkClient: RetrofitNetworkClient
) : VacanciesRepository {
    override suspend fun search(text: String, page: Int): ResponseState {
        val query = mapOf(
            "text" to text,
            "page" to page.toString(),
            "per_page" to PAGINATION_COUNT_PAGES
        )
        val response = retrofitNetworkClient.doRequest(query)
        return if (response.resultCode == 200 && response is VacanciesResponse) {
            val listVacancies = response.items.asDomain()

            if (listVacancies.isEmpty()) {
                ResponseState.Empty
            } else {
                ResponseState.ContentVacanciesList(listVacancies, response.found, response.pages)
            }

        } else if (response.resultCode >= 400) {
            ResponseState.ServerError
        } else {
            ResponseState.NetworkError
        }
    }

    override suspend fun searchById(id: String): ResponseState {
        val response = retrofitNetworkClient.doRequestById(id)
        return if (response.resultCode == 200 && response is VacancyDetailDto) {
            ResponseState.ContentVacancyDetail(response.asDomain())
        } else if (response.resultCode == 400) {
            ResponseState.ServerError
        } else {
            ResponseState.NetworkError
        }
    }
}


