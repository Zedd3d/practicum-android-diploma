package ru.practicum.android.diploma.presentation.filters.main.state

sealed interface FiltersMainViewState {
    data class Content(
        val workPlace: String,
        val industries: String,
        val salary: Int,
        val onlyWithSalary: Boolean
    ) : FiltersMainViewState

    object Empty : FiltersMainViewState
}
