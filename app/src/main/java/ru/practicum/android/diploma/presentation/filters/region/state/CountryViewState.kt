package ru.practicum.android.diploma.presentation.filters.region.state

import ru.practicum.android.diploma.domain.models.Area

sealed interface CountryViewState {
    data class Content(val listAreas: List<Area>) : CountryViewState
    data object Error : CountryViewState

    data object Loading : CountryViewState
}
