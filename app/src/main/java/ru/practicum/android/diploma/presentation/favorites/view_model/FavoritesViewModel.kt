package ru.practicum.android.diploma.presentation.favorites.view_model

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.practicum.android.diploma.domain.impl.FavoritesRepository
import ru.practicum.android.diploma.presentation.general.view_model.ViewState
import javax.inject.Inject

class FavoritesViewModel @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {
    private val state = MutableStateFlow(ViewState())
    private var isNextPageLoading = false
    fun observeUi() = state.asStateFlow()
}
