package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.data.dto.VacanciesResponse
import ru.practicum.android.diploma.data.dto.VacancyAreaDto
import ru.practicum.android.diploma.data.dto.IndustryDto
import ru.practicum.android.diploma.data.dto.detail.VacancyDetailDto

interface HeadHunterService {

    @GET("/vacancies")
    suspend fun vacancies(@QueryMap response: Map<String, String>): VacanciesResponse

    @GET("/vacancies/{id}")
    suspend fun getVacancyById(@Path("id") id: String): VacancyDetailDto

    @GET("/areas")
    suspend fun getAreas(): List<VacancyAreaDto>

    @GET("/areas/{area_id}")
    suspend fun getAreaById(@Path("area_id") id: String): VacancyAreaDto

    @GET("industries")
    suspend fun filterIndustry(): List<IndustryDto>
}
