package ru.practicum.android.diploma.presentation.general

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.impl.VacanciesRepository
import ru.practicum.android.diploma.domain.models.Vacancy
import javax.inject.Inject

class GeneralViewModel @Inject constructor(
    private val vacanciesRepository: VacanciesRepository
): ViewModel() {

    private val state = MutableStateFlow(ViewState())
    fun observeUi() = state.asStateFlow()

    fun search(query: String){
        viewModelScope.launch {
            val vacancies = vacanciesRepository.search(query)
            state.update { it.copy(vacancies = vacancies) }
        }
    }
}

data class ViewState(
    val vacancies: List<Vacancy> = emptyList()
)
