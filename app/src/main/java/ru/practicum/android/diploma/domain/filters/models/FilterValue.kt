package ru.practicum.android.diploma.domain.filters.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilterValue(
    val id: String,
    val parentId: String = "",
    val name: String,
    val valueString: String = "",
    val valueInt: Int = 0,
    val valueBoolean: Boolean = false,
) : Parcelable
