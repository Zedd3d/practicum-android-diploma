package ru.practicum.android.diploma.data.dto

data class VacancyDto(
    val id: String,
    val department: DepartmentDto?,
    val name: String,
    val area: VacancyAreaDto,
    val employer: EmployerDto,
    val salary: SalaryDto?,
    val type: VacancyTypeDto
)
