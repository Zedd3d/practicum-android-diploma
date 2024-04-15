package ru.practicum.android.diploma.data.converters

import androidx.room.TypeConverter
import ru.practicum.android.diploma.data.dto.IndustryDto
import ru.practicum.android.diploma.domain.filters.industry.models.Industry
import ru.practicum.android.diploma.domain.filters.industry.models.SubIndustry

object FiltersConvertor {
    @TypeConverter
    fun convertorToIndustry(industry: IndustryDto): Industry {
        val subindustries: MutableList<SubIndustry>? = mutableListOf()
        for (subindustry in industry.industries) {
            subindustries?.add(
                SubIndustry(
                    id = subindustry.id,
                    name = subindustry.name
                )
            )
        }
        return Industry(
            id = industry.id,
            name = industry.name ?: "",
            industries = subindustries ?: emptyList()
        )
    }
}
