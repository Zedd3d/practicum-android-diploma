package ru.practicum.android.diploma.presentation.filters.main.state

sealed interface FiltersMainViewState {
    data class Content(
        val workPlace: String,
        val industries: String,
        val salary: String,
        val onlyWithSalary: Boolean,
        val currencyHard: Int,
        val filterChanged: Boolean,
        val filtresAvailable: Boolean,
    ) : FiltersMainViewState

    object Empty : FiltersMainViewState
}
