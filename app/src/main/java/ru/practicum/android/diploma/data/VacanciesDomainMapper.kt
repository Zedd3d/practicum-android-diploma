package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.data.dto.SalaryDto
import ru.practicum.android.diploma.data.dto.VacancyDto
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.Vacancy

fun List<VacancyDto>.asDomain(): List<Vacancy> = this.map { it.asDomain() }

fun VacancyDto.asDomain(): Vacancy = Vacancy(
    id = id,
    img = this.employer.logoUrls?.original,
    employer = this.employer.name,
    name = name,
    salary = salary?.asDomain(),
    area = this.area.name
)

fun SalaryDto.asDomain(): Salary = Salary(
    currency = currency,
    from = from,
    gross = gross,
    to = to
)
