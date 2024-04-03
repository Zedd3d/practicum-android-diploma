package ru.practicum.android.diploma.domain.models

import ru.practicum.android.diploma.data.dto.detail.EmploymentDto
import ru.practicum.android.diploma.data.dto.detail.ExperienceDto

data class VacancyDetail(
    val id: String,
    val name: String,
    val salary: Salary?,
    val experience: String?,
    val description: String?,
    val employer: Employer?,
    val keySkills: List<String>?,
    val area: String?,
    val employment: String?
)
