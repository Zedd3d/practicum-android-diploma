package ru.practicum.android.diploma.presentation.general.fragment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.App
import ru.practicum.android.diploma.databinding.FragmentGeneralBinding
import ru.practicum.android.diploma.domain.filters.models.FilterValue
import ru.practicum.android.diploma.domain.general.models.ResponseState
import ru.practicum.android.diploma.presentation.Factory
import ru.practicum.android.diploma.presentation.filters.region.fragment.FiltersWorkPlaceFragment
import ru.practicum.android.diploma.presentation.filters.region.viewmodel.FiltersWorkPlaceViewModel
import ru.practicum.android.diploma.presentation.general.VacanciesAdapter
import ru.practicum.android.diploma.presentation.general.viewmodel.GeneralViewModel
import ru.practicum.android.diploma.util.onTextChangeDebounce
import ru.practicum.android.diploma.util.visibleOrGone

private const val DEBOUNCE: Long = 2000

class GeneralFragment : Fragment(R.layout.fragment_general) {

    private val viewModel by viewModels<GeneralViewModel> {
        Factory {
            (requireContext().applicationContext as App).appComponent.generalComponent().viewModel()
        }
    }

    companion object {
        const val ON_FILTER_CHANGED = "on_filter_changed"
    }

    private var _binding: FragmentGeneralBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy {
        VacanciesAdapter() {
            val params = bundleOf("id" to it)
            findNavController().navigate(R.id.action_generalFragment_to_vacancyFragment, params)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentGeneralBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupVacancies()
        setListeners()
        setObservers()

        setFragmentResultListener(ON_FILTER_CHANGED) { s: String, bundle: Bundle ->
            viewModel.searchOnFilterChanged()
        }

    }

    private fun setObservers() {
        viewModel.observeUi().observe(viewLifecycleOwner) { state ->
            if (state is ResponseState.ContentVacanciesList) {
                adapter.submitList(state.listVacancy)
                checkFilters(state)
            }
            updateStatus(state)
            binding.vacanciesProgress.isVisible = when (state) {
                is ResponseState.Loading -> state.isPagination
                else -> false
            }

            binding.foundCountText.text = when (state) {
                is ResponseState.ContentVacanciesList -> {
                    if (state.found > 0) {
                        getString(R.string.found_count, state.found.toString()).plus(" ").plus(getNoun(state.found))
                    } else {
                        null
                    }
                }

                else -> getString(R.string.no_vacancies_lil)
            }

            binding.vacanciesLoading.isVisible = when (state) {
                is ResponseState.Loading -> !state.isPagination
                else -> false
            }
            if (state is ResponseState.Loading) hideKeyBoard()

        }
    }

    private fun setListeners() {
        binding.searchEditText.onTextChangeDebounce().debounce(DEBOUNCE)
            .onEach {
                val query = it?.toString().orEmpty()
                viewModel.search(query)
            }.launchIn(lifecycleScope)

        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                setupIcon(p0.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
        })

        binding.clearButton.setOnClickListener {
            binding.searchEditText.text = null
        }

        binding.vacanciesRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val pos = (binding.vacanciesRv.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    val itemsCount = adapter.itemCount
                    if (pos >= itemsCount - 1) {
                        viewModel.onLastItemReached()
                    }
                }
            }
        })
        binding.filterImageView.setOnClickListener {
            findNavController().navigate(
                R.id.action_generalFragment_to_filtersMainFragment
            )
        }
    }

    private fun updateStatus(state: ResponseState) {
        updatePreStatus(state)
        when (state) {
            ResponseState.Empty -> {
                binding.srcText.setText(R.string.no_vacancies)
                binding.foundCountText.setText(R.string.no_vacancies_lil)
            }

            ResponseState.ServerError -> {
                binding.srcText.setText(R.string.server_error)
            }

            ResponseState.NetworkError(false) -> {
                binding.srcText.setText(R.string.no_internet)
            }

            ResponseState.NetworkError(true) -> {
                binding.src.isVisible = false
                Toast.makeText(requireContext(), "Ошибка соединения", Toast.LENGTH_SHORT).show()
            }

            else -> {
                binding.srcText.text = ""
            }
        }
        updatePicture(state)
    }

    private fun updatePreStatus(state: ResponseState) {
        binding.vacanciesRv.isVisible = when (state) {
            is ResponseState.Loading -> state.isPagination
            is ResponseState.ContentVacanciesList -> true
            is ResponseState.NetworkError -> true
            else -> false
        }
        binding.src.visibleOrGone(state !is ResponseState.Loading && state !is ResponseState.ContentVacanciesList)
        binding.srcText.visibleOrGone(binding.src.isVisible)
        binding.foundCount.visibleOrGone(state is ResponseState.ContentVacanciesList || state is ResponseState.Empty)
    }

    private fun updatePicture(status: ResponseState) {
        val image = when (status) {
            ResponseState.Empty -> {
                R.drawable.state_image_nothing_found
            }

            ResponseState.ServerError -> {
                R.drawable.state_image_server_error_search
            }

            ResponseState.NetworkError(false) -> {
                R.drawable.state_image_no_internet
            }

            ResponseState.Start -> {
                R.drawable.state_image_start_search
            }

            else -> {
                null
            }
        }

        image?.let {
            Glide.with(requireContext())
                .load(image)
                .into(binding.src)
        }
    }

    private fun checkFilters(status: ResponseState.ContentVacanciesList) {
        val image = when (status.isWithFilters) {
            true -> R.drawable.ic_filter_on
            else -> R.drawable.ic_filter
        }
        image?.let {
            Glide.with(requireContext())
                .load(image)
                .into(binding.src)
        }
    }

    private fun setupVacancies() {
        binding.vacanciesRv.adapter = adapter
        binding.vacanciesRv.layoutManager = LinearLayoutManager(requireContext())
        binding.foundCount.visibleOrGone(false)

    }

    private fun hideKeyBoard() {
        if (_binding == null) return
        binding.let {
            val inputMethodManager =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(binding.searchEditText.windowToken, 0)
        }
    }

    private fun setupIcon(it: String) {
        if (it.isNotBlank()) {
            binding.searchEditText.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_clear, 0)
            binding.clearButton.isEnabled = true
        } else {
            hideKeyBoard()
            binding.searchEditText.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_search, 0)
            binding.clearButton.isEnabled = false
        }
    }

    private fun getNoun(count: Int): String {
        return when (count.toString().last()) {
            '1' -> getString(R.string.NounOne)
            '2' -> getString(R.string.NounTwo)
            '3' -> getString(R.string.NounTwo)
            '4' -> getString(R.string.NounTwo)
            else -> getString(R.string.NounThree)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)?.isVisible = true
    }
}
