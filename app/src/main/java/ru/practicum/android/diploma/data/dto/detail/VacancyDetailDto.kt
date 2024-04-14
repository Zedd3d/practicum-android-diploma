package ru.practicum.android.diploma.data.dto.detail

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.data.dto.EmployerDto
import ru.practicum.android.diploma.data.dto.SalaryDto
import ru.practicum.android.diploma.data.dto.VacancyAreaDto
import ru.practicum.android.diploma.data.network.Response

data class VacancyDetailDto(
    val id: String,
    val name: String,
    val salary: SalaryDto?,
    val experience: ExperienceDto?,
    val description: String?,
    val employer: EmployerDto?,
    @SerializedName("key_skills") val keySkills: List<KeySkillDto>?,
    val area: VacancyAreaDto?,
    val employment: EmploymentDto?,
    val schedule: ScheduleDto?,
    @SerializedName("alternate_url") val alternateUrl: String
) : Response()
