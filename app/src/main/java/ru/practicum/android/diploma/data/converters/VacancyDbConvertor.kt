package ru.practicum.android.diploma.data.converters

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.practicum.android.diploma.data.favorites.entity.FavoritesVacanciesEntity
import ru.practicum.android.diploma.domain.models.Contacts
import ru.practicum.android.diploma.domain.models.Phone
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyDetail

object VacancyDbConvertor {
    fun map(vacancy: VacancyDetail): FavoritesVacanciesEntity {
        val gson = Gson()
        return FavoritesVacanciesEntity(
            vacancy.id,
            vacancy.employer?.logoUrls ?: "",
            vacancy.employer?.name ?: "",
            vacancy.name,
            vacancy.area ?: "",
            gson.toJson(vacancy),
            vacancy.salary?.currency ?: "",
            vacancy.salary?.from ?: 0,
            vacancy.salary?.to ?: 0,
            vacancy.salary?.gross ?: false
        )
    }

    fun mapToVacancy(vacancy: FavoritesVacanciesEntity): Vacancy {
        return Vacancy(
            vacancy.id,
            vacancy.img,
            vacancy.employer,
            vacancy.name,
            Salary(
                vacancy.salaryCurrency,
                vacancy.salaryFrom,
                vacancy.salaryGross,
                vacancy.salaryTo
            ),
            vacancy.area,
            Contacts(
                vacancy.contactsEmail,
                vacancy.contactsName,
                List<Phone>() // Не очень понятно, как это правильно записать
            )
        )
    }

    fun mapToVacancyDetail(vacancy: FavoritesVacanciesEntity): VacancyDetail {
        val gson = Gson()
        return gson.fromJson<VacancyDetail>(
            vacancy.vacancyDetailData,
            object : TypeToken<VacancyDetail>() {}.type
        )
    }
}
