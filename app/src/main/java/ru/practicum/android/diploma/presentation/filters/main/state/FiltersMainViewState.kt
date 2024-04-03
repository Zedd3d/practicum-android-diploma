package ru.practicum.android.diploma.presentation.filters.main.state

sealed interface FiltersMainViewState {
    data class Content(
        val workPlace: String,
        val Industries: String
    ) : FiltersMainViewState

    object Empty : FiltersMainViewState
}
