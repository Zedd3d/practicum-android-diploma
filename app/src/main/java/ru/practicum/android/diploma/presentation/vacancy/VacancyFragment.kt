package ru.practicum.android.diploma.presentation.vacancy


import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.App
import ru.practicum.android.diploma.presentation.Factory

class VacancyFragment : Fragment(R.layout.vacancy_information_fragment) {
    private val vacancyId: String? by lazy { requireArguments().getString("id") }
    private val viewModel by viewModels<VacancyViewModel>{
        Factory{
            App.appComponent.vacancyComponent().create(requireNotNull(vacancyId)).viewModel()
        }
    }
}
