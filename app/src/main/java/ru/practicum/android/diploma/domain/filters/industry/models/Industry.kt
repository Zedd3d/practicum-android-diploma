package ru.practicum.android.diploma.domain.filters.industry.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kotlinx.parcelize.Parcelize
import ru.practicum.android.diploma.data.converters.VacancyDbConvertor
import ru.practicum.android.diploma.domain.filters.industry.impl.IndustryInteractorImpl
import ru.practicum.android.diploma.domain.filters.industry.impl.IndustryRepositoryImpl
import ru.practicum.android.diploma.presentation.filters.industry.viewmodel.IndustryViewModel

@Entity
@Parcelize
data class Industry(
    @PrimaryKey
    val id: String,
    val name: String,
    //@TypeConverters(FiltersConvertor::class)
    val industries: List<SubIndustry>?
) : Parcelable
