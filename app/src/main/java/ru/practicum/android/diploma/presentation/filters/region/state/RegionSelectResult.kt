package ru.practicum.android.diploma.presentation.filters.region.state

import ru.practicum.android.diploma.domain.filters.models.FilterValue

data class RegionSelectResult(
    val filterCountry: FilterValue,
    val filterRegion: FilterValue
)
