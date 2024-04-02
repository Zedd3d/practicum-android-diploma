package ru.practicum.android.diploma.presentation.vacancy

import androidx.lifecycle.ViewModel
import javax.inject.Inject

class VacancyViewModel @Inject constructor(
    val vacancyId: String
) : ViewModel()
