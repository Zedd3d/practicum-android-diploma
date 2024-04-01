package ru.practicum.android.diploma.presentation.vacancy


import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.App
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.presentation.Factory

class VacancyFragment : Fragment(R.layout.fragment_vacancy) {

    private val vacancyId: String? by lazy { requireArguments().getString("id") }

    private val binding by viewBinding(FragmentVacancyBinding:: bind)

    private val viewModel by viewModels<VacancyViewModel>{
        Factory{
            App.appComponent.vacancyComponent().create(requireNotNull(vacancyId)).viewModel()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
