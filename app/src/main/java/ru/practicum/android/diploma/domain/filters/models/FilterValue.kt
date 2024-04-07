package ru.practicum.android.diploma.domain.filters.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilterValue(
    val id: String,
    val parent_id: String = "",
    val name: String,
    val valueString: String,
    val valueInt: Int,
    val valueBoolean: Boolean,
) : Parcelable
