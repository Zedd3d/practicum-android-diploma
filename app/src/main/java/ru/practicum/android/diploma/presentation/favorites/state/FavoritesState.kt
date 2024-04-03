package ru.practicum.android.diploma.presentation.favorites.state


sealed class FavoritesState {
    object Loading : FavoritesState()

    data object Content : FavoritesState()

    data object Empty : FavoritesState()

    data object NetworkError : FavoritesState()
}
