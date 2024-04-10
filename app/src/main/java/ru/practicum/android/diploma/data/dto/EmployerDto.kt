package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class EmployerDto(
    val id: String,
    @SerializedName("logo_urls") val logoUrls: LogoUrlsDto?,
    val name: String,
    val trusted: Boolean,
    @SerializedName("vacancies_url") val vacanciesUrl: String
)
