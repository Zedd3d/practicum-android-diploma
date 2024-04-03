package ru.practicum.android.diploma.presentation.vacancy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.App
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.presentation.Factory

class VacancyFragment : Fragment(R.layout.fragment_vacancy) {
    private val vacancyId: String? by lazy { requireArguments().getString("id") }

    private var _binding: FragmentVacancyBinding? = null
    private val binding get() = _binding!!


    private val viewModel by viewModels<VacancyViewModel> {
        Factory {
            App.appComponent.vacancyComponent().create(requireNotNull(vacancyId)).viewModel()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentVacancyBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
