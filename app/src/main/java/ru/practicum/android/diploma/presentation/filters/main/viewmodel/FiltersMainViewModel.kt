package ru.practicum.android.diploma.presentation.filters.main.viewmodel

import androidx.lifecycle.ViewModel
import javax.inject.Inject

class FiltersMainViewModel @Inject constructor() : ViewModel() {

    // private val state = MutableStateFlow(ViewState())

    private var isNextPageLoading = false

    private var query: String? = null

}

