package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.data.entity.FavoritesVacanciesEntity
import ru.practicum.android.diploma.domain.favorites.models.FavoriteDbModel

object VacancyDbConvertor {
    fun map(vacancy: FavoriteDbModel): FavoritesVacanciesEntity {
        return FavoritesVacanciesEntity(
            vacancy.idDto,
            vacancy.nameDto,
            vacancy.salaryDto,
            vacancy.experienceDto,
            vacancy.descriptionDto,
            vacancy.employerDto,
            vacancy.keySkillsDto,
            vacancy.areaDto,
            vacancy.employmentDto
        )
    }

    fun map(vacancy: FavoritesVacanciesEntity): FavoriteDbModel {
        return FavoriteDbModel(
            vacancy.id,
            vacancy.name,
            vacancy.salary,
            vacancy.experience,
            vacancy.description,
            vacancy.employer,
            vacancy.keySkills,
            vacancy.area,
            vacancy.employment
        )
    }
}
