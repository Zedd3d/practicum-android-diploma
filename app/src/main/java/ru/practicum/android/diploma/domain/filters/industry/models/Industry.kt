package ru.practicum.android.diploma.domain.filters.industry.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Industry(
    @PrimaryKey
    val id: String,
    val name: String,
    //@TypeConverters(FiltersConvertor::class)
    val industries: List<SubIndustry>?
) : Parcelable
