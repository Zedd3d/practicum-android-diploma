package ru.practicum.android.diploma.presentation.filters.main.view_model

import androidx.lifecycle.ViewModel
import javax.inject.Inject

class FiltersMainViewModel @Inject constructor(

) : ViewModel() {

    //private val state = MutableStateFlow(ViewState())

    private var isNextPageLoading = false

    private var query: String? = null

    private val PAG_COUNT: Int = 20

}

