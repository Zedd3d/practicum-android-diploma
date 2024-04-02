package ru.practicum.android.diploma.di.favorites

import dagger.Subcomponent
import ru.practicum.android.diploma.presentation.favorites.view_model.FavoritesViewModel

@Subcomponent
interface FavoritesComponent {
    fun viewModel(): FavoritesViewModel
}
