package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.data.dto.VacanciesResponse
import ru.practicum.android.diploma.data.dto.detail.VacancyDetailDto

interface HeadHunterService {

    @GET("/vacancies")
    suspend fun vacancies(@QueryMap response: Map<String, String>): VacanciesResponse

    @GET("/vacancies/{id}")
    suspend fun getVacancyById(@Path("id") id: String): VacancyDetailDto
}
