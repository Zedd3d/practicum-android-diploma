package ru.practicum.android.diploma.presentation.filters.region.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
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
            (requireContext().applicationContext as App).appComponent.workPlaceComponent().viewModel()
        }
    }

    private var _binding: FragmentFiltersWorkplaceBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val RESULT_NAME_COUNTRY = "result_country"
        const val RESULT_NAME_REGION = "result_region"
        const val COUNTRY_ID = "countryId"
        fun createArgs(countryId: String): Bundle =
            bundleOf(COUNTRY_ID to countryId)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFiltersWorkplaceBinding.inflate(layoutInflater)
        binding.llCountry.smallTextBlock.setText(getString(R.string.filter_country))
        binding.llRegion.smallTextBlock.setText(getString(R.string.filter_region))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        binding.llCountry.ivBtnClear.setOnClickListener {
            viewModel.clearCountry()
        }

        binding.llRegion.ivBtnClear.setOnClickListener {
            viewModel.clearRegion()
        }

        binding.llCountry.root.setOnClickListener {
            findNavController().navigate(
                R.id.action_filtersWorkPlaceFragment_to_filtersCountryFragment
            )
        }

        setFragmentResultListener(RESULT_NAME_COUNTRY) { s: String, bundle: Bundle ->
            bundle.let {
                val filterValue = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    it.getParcelable(s, FilterValue::class.java)
                } else {
                    it.getParcelable<FilterValue>(s)
                }

                viewModel.setFilterCountry(filterValue)
            }
        }


        setFragmentResultListener(RESULT_NAME_REGION) { s: String, bundle: Bundle ->
            bundle.let {
                val filterValue = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    it.getParcelable(s, FilterValue::class.java)
                } else {
                    it.getParcelable<FilterValue>(s)
                }

                val filterValueCountry = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    it.getParcelable(s, FilterValue::class.java)
                } else {
                    it.getParcelable<FilterValue>(s)
                }

                viewModel.setFilterRegion(filterValue)
            }
        }

        binding.llRegion.root.setOnClickListener {
            viewModel.selectRegion()
        }

        binding.btnSelect.setOnClickListener {
            viewModel.saveFilters()
            onBackPressed()
        }

        viewModel.getState().observe(viewLifecycleOwner) { state ->
            onChangeViewState(state)
        }

        viewModel.getFilterChanged().observe(viewLifecycleOwner) { filterChanged ->
            onFilterChanged(filterChanged)
        }

        viewModel.getSelectRegion().observe(viewLifecycleOwner) { countryId ->
            selectRegion(countryId)
        }
    }

    private fun selectRegion(countryId: String) {
        findNavController().navigate(
            R.id.action_filtersWorkPlaceFragment_to_filtersRegionFragment,
            createArgs(countryId)
        )
    }

    private fun onFilterChanged(filterChanged: Boolean) {
        binding.btnSelect.isVisible = filterChanged
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

    private fun onBackPressed() {
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
