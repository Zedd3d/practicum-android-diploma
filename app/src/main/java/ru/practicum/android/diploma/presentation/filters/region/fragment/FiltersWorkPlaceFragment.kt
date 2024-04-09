package ru.practicum.android.diploma.presentation.filters.region.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.App
import ru.practicum.android.diploma.databinding.FragmentFiltersWorkplaceBinding
import ru.practicum.android.diploma.domain.filters.models.FilterValue
import ru.practicum.android.diploma.presentation.Factory
import ru.practicum.android.diploma.presentation.filters.CustomViewPropertysSetter.setViewPropertys
import ru.practicum.android.diploma.presentation.filters.region.state.FiltersWorkPlaceViewState
import ru.practicum.android.diploma.presentation.filters.region.viewmodel.FiltersWorkPlaceViewModel

class FiltersWorkPlaceFragment : Fragment(R.layout.fragment_filters_workplace) {

    private val viewModel by viewModels<FiltersWorkPlaceViewModel> {
        Factory {
            App.appComponent.generalComponent().viewModel()
        }
    }

    private var _binding: FragmentFiltersWorkplaceBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: FiltersAreaAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFiltersWorkplaceBinding.inflate(layoutInflater)
        binding.llCountry.smallTextBlock.setText(getString(R.string.filter_country))
        binding.llRegion.smallTextBlock.setText(getString(R.string.filter_region))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FiltersAreaAdapter(emptyList<FilterValue>()) { filterValue: FilterValue ->
            clickListener(
                filterValue
            )
        }

        activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)?.isVisible = false

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressed()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.llCountry.root.setOnClickListener {
            findNavController().navigate(
                R.id.action_filtersWorkPlaceFragment_to_filtersCountryFragment
            )
        }

        binding.llRegion.root.setOnClickListener {
            findNavController().navigate(
                R.id.action_filtersWorkPlaceFragment_to_filtersRegionFragment
            )
        }

        onChangeViewState(FiltersWorkPlaceViewState.Empty)

    }

    private fun onChangeViewState(state: FiltersWorkPlaceViewState) {
        when (state) {
            is FiltersWorkPlaceViewState.Empty -> {
                setViewPropertys(binding.llCountry, "")
                setViewPropertys(binding.llRegion, "")
            }

            is FiltersWorkPlaceViewState.Content -> {
                setViewPropertys(binding.llCountry, state.country)
                setViewPropertys(binding.llRegion, state.region)
            }
        }
    }


    private fun clickListener(filterValue: FilterValue) {

    }

    private fun onBackPressed() {
        activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)?.isVisible = true
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
