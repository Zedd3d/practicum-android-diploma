package ru.practicum.android.diploma.data.favorites.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.practicum.android.diploma.data.dto.PhoneDto
import java.util.Calendar

@Entity(tableName = "favorites_vacancies_table")
data class FavoritesVacanciesEntity(
    @PrimaryKey
    val id: String,
    val img: String = "",
    val employer: String = "",
    val name: String = "",
    val area: String = "",
    val vacancyDetailData: String = "",
    val salaryCurrency: String = "",
    val salaryFrom: Int = 0,
    val salaryTo: Int = 0,
    val salaryGross: Boolean = false,
    val inDbTime: Long = Calendar.getInstance().time.time,
    val contactsEmail: String = "",
    val contactsFormattedPhone: String = "",
    val contactsComment: String = "",
    val contactsName: String = ""
)
