package ru.practicum.android.diploma.domain.filters.industry.models

import android.os.Parcelable
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class Industry(
    @PrimaryKey
    val id: String,
    val name: String,
    val industries: List<SubIndustry>?
) : Parcelable
