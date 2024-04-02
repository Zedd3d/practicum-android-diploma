package ru.practicum.android.diploma.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.practicum.android.diploma.data.dto.DepartmentDto
import ru.practicum.android.diploma.data.dto.EmployerDto
import ru.practicum.android.diploma.data.dto.SalaryDto
import ru.practicum.android.diploma.data.dto.VacancyAreaDto
import ru.practicum.android.diploma.data.dto.VacancyTypeDto

@Entity(tableName = "favorites_vacancies_table")
data class FavoritesVacanciesEntity(
    @PrimaryKey
    val id: String,
    val department: String,
    val name: String,
    val area: String,
    val employer: String,
    val salary: String,
    val type: String
)

// TODO Доделать
