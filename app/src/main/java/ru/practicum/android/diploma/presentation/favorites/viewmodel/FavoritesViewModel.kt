package ru.practicum.android.diploma.presentation.favorites.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.practicum.android.diploma.domain.favorites.api.FavoritesInteractor
import ru.practicum.android.diploma.presentation.general.viewmodel.ViewState
import javax.inject.Inject

class FavoritesViewModel @Inject constructor(
    private val favoritesInteractor: FavoritesInteractor
) : ViewModel() {
    private val state = MutableStateFlow(ViewState())
    private var isNextPageLoading = false
    fun observeUi() = state.asStateFlow()
}
