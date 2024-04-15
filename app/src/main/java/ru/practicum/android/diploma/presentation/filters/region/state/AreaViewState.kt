package ru.practicum.android.diploma.presentation.filters.region.state

import ru.practicum.android.diploma.domain.models.Area

sealed interface AreaViewState {
    data class Content(val listAreas: List<Area>) : AreaViewState
    data object Error : AreaViewState
    data object Empty : AreaViewState

    data object Loading : AreaViewState
}
