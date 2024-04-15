package ru.practicum.android.diploma.data.dto

import ru.practicum.android.diploma.domain.filters.industry.models.SubIndustry

data class IndustryDto(
    val id: String,
    val name: String,
    val industries: List<SubIndustryDto>
)
