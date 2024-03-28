package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.data.dto.VacanciesResponse

interface HeadHunterService {

    @GET("/vacancies")
    suspend fun vacancies(@QueryMap response: Map<String, String>): VacanciesResponse
}
