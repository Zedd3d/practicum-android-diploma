package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.data.network.Response

data class VacancyAreaDto(
    val id: String,
    @SerializedName("parent_id") val parentId: String?,
    val name: String,
    val url: String = "",
    val areas: List<VacancyAreaDto> = emptyList<VacancyAreaDto>()
) : Response()
