package ru.practicum.android.diploma.data.dto.detail

import ru.practicum.android.diploma.data.dto.EmployerDto
import ru.practicum.android.diploma.data.dto.SalaryDto

data class VacancyDetailDto(
    val id: String,
    val name: String,
    val salary: SalaryDto?,
    val experience: ExperienceDto?,
    val description: String?,
    val employerDto: EmployerDto?,
    val keySkills: List<KeySkillDto>
)
