package ru.practicum.android.diploma.presentation.filters.main.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.App
import ru.practicum.android.diploma.databinding.FragmentFiltersMainBinding
import ru.practicum.android.diploma.presentation.Factory
import ru.practicum.android.diploma.presentation.filters.CustomViewPropertysSetter.setViewPropertys
import ru.practicum.android.diploma.presentation.filters.main.state.FiltersMainViewState
import ru.practicum.android.diploma.presentation.filters.main.viewmodel.FiltersMainViewModel
import ru.practicum.android.diploma.util.onTextChange

class FiltersMainFragment : Fragment(R.layout.fragment_filters_main) {

    private val viewModel by viewModels<FiltersMainViewModel> {
        Factory {
            App.appComponent.filtersMainComponent().viewModel()
        }
    }

    private var _binding: FragmentFiltersMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFiltersMainBinding.inflate(layoutInflater)
        binding.llWorkPlace.smallTextBlock.setText(getString(R.string.filter_workplace))
        binding.llIndustries.smallTextBlock.setText(getString(R.string.filter_industries))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCancel.isSelected = true
        activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)?.isVisible = false

        binding.tietSalary.onTextChange {
            setHintTextColor(it)
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressed()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.btnAccept.setOnClickListener {
            Toast.makeText(context, "Принять", Toast.LENGTH_SHORT).show()
        }

        binding.btnCancel.setOnClickListener {
            Toast.makeText(context, "Сбросить", Toast.LENGTH_SHORT).show()
        }

        binding.llWorkPlace.root.setOnClickListener {
            findNavController().navigate(
                R.id.action_filtersMainFragment_to_filtersWorkPlaceFragment
            )
        }

        viewModel.getState().observe(viewLifecycleOwner) { state ->
            onChangeViewState(state)
        }
    }

    private fun onBackPressed() {
        activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)?.isVisible = true
        findNavController().popBackStack()
    }

    @SuppressLint("ResourceAsColor")
    private fun setHintTextColor(salaryText: String) {
        binding.tvSalaryHint.isEnabled = salaryText.isNotBlank()
    }

    private fun onChangeViewState(state: FiltersMainViewState) {
        when (state) {
            is FiltersMainViewState.Empty -> {
                setViewPropertys(binding.llWorkPlace, "")
                setViewPropertys(binding.llIndustries, "")
            }

            is FiltersMainViewState.Content -> {
                setViewPropertys(binding.llWorkPlace, state.workPlace)
                setViewPropertys(binding.llIndustries, state.industries)
            }
        }

        binding.btnCancel.isVisible = false
        binding.btnAccept.isVisible = false
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
