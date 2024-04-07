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
import ru.practicum.android.diploma.databinding.FragmentFiltersRegionBinding
import ru.practicum.android.diploma.domain.filters.models.FilterValue
import ru.practicum.android.diploma.presentation.Factory
import ru.practicum.android.diploma.presentation.filters.main.state.FiltersMainViewState
import ru.practicum.android.diploma.presentation.filters.region.viewmodel.FiltersRegionViewModel

class FiltersRegionFragment : Fragment(R.layout.fragment_filters_region) {

    private val viewModel by viewModels<FiltersRegionViewModel> {
        Factory {
            App.appComponent.generalComponent().viewModel()
        }
    }

    private var _binding: FragmentFiltersRegionBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: FiltersAreaAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFiltersRegionBinding.inflate(layoutInflater)
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
        onChangeViewState(FiltersMainViewState.Empty)

    }

    private fun clickListener(filterValue: FilterValue) {

    }

    private fun onBackPressed() {
        activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)?.isVisible = true
        findNavController().popBackStack()
    }


    private fun onChangeViewState(state: FiltersMainViewState) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
