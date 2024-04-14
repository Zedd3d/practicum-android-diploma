package ru.practicum.android.diploma.data.filters

import ru.practicum.android.diploma.data.dto.VacancyAreaDto
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.data.network.models.AreasResponse
import ru.practicum.android.diploma.domain.filters.main.api.FiltersRepository
import ru.practicum.android.diploma.domain.filters.models.ResponseStateArea
import ru.practicum.android.diploma.domain.models.Area
import javax.inject.Inject

class FiltersRepositoryImpl @Inject constructor(
    private val retrofitNetworkClient: RetrofitNetworkClient
) : FiltersRepository {

    companion object {
        const val HTTP_OK = 200
        const val HTTP_CLIENT_ERROR = 400
    }

    override suspend fun getAreas(): ResponseStateArea {
        val response = retrofitNetworkClient.getAreas()
        return if (response.resultCode == HTTP_OK && response is AreasResponse) {
            val listAreas = response.items.map { it.asDomain() }

            if (listAreas.isEmpty()) {
                ResponseStateArea.Empty
            } else {
                ResponseStateArea.ContentArea(listAreas)
            }
        } else if (response.resultCode >= HTTP_CLIENT_ERROR) {
            ResponseStateArea.ServerError
        } else {
            ResponseStateArea.NetworkError
        }
    }
}

private fun VacancyAreaDto.asDomain(): Area {
    return Area(
        id,
        parentId ?: "",
        name,
        areas.map { it -> it.asDomain() }
    )
}
