package ru.practicum.android.diploma.presentation.general.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.App
import ru.practicum.android.diploma.databinding.FragmentGeneralBinding
import ru.practicum.android.diploma.presentation.Factory
import ru.practicum.android.diploma.presentation.general.VacanciesAdapter
import ru.practicum.android.diploma.presentation.general.viewmodel.GeneralViewModel
import ru.practicum.android.diploma.presentation.general.viewmodel.ResponseState
import ru.practicum.android.diploma.util.onTextChange
import ru.practicum.android.diploma.util.onTextChangeDebounce
import ru.practicum.android.diploma.util.visibleOrGone

private const val DEBOUNCE: Long = 2000

class GeneralFragment : Fragment(R.layout.fragment_general) {

    private val viewModel by viewModels<GeneralViewModel> {
        Factory {
            App.appComponent.generalComponent().viewModel()
        }
    }

    private var _binding: FragmentGeneralBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy {
        VacanciesAdapter() {
            val params = bundleOf("id" to it)
            findNavController().navigate(R.id.action_generalFragment_to_vacancyFragment, params)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentGeneralBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupVacancies()
        binding.searchEditText.onTextChangeDebounce()
            .debounce(DEBOUNCE)
            .onEach {
                val query = it?.toString().orEmpty()
                viewModel.search(query)
            }
            .launchIn(lifecycleScope)

        binding.searchEditText.onTextChange {
            setupIcon(it)
        }

        binding.clearButton.setOnClickListener {
            binding.searchEditText.setText("")
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.observeUi().collect { state ->
                    adapter.submitList(state.vacancies)
                    updateStatus(state.status)

                    binding.vacanciesProgress.visibleOrGone(state.vacanciesProgress)

                    binding.foundCountText.text = if (state.found != 0) {
                        getString(R.string.found_count, state.found.toString())
                    } else {
                        getString(R.string.no_vacancies_lil)
                    }
                    binding.vacanciesLoading.visibleOrGone(state.isLoading)
                    if (state.isLoading) {
                        hideKeyBoard()
                    }
                }
            }
        }

        binding.vacanciesRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) {
                    val pos = (binding.vacanciesRv.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    val itemsCount = adapter.itemCount
                    if (pos >= itemsCount - 1) {
                        viewModel.onLastItemReached(binding.searchEditText.text.toString())
                        binding.vacanciesProgress.visibleOrGone(true)
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

    private fun updateStatus(status: ResponseState) {
        binding.vacanciesRv.visibleOrGone(status == ResponseState.Content)
        binding.src.visibleOrGone(status != ResponseState.Content)
        binding.srcText.visibleOrGone(status != ResponseState.Content)
        binding.foundCount.visibleOrGone(status == ResponseState.Content || status == ResponseState.Empty)
        when (status) {
            ResponseState.Empty -> {
                binding.srcText.setText(R.string.no_vacancies)
                binding.foundCountText.setText(R.string.no_vacancies_lil)
            }

            ResponseState.ServerError -> {
                binding.srcText.setText(R.string.server_error)
            }

            ResponseState.NetworkError -> {
                binding.srcText.setText(R.string.no_internet)
            }

            else -> {
                binding.srcText.text = ""
            }
        }
        updatePicture(status)
    }

    private fun updatePicture(status: ResponseState) {
        val image = when (status) {
            ResponseState.Empty -> {
                R.drawable.state_image_nothing_found
            }

            ResponseState.ServerError -> {
                R.drawable.state_image_server_error_search
            }

            ResponseState.NetworkError -> {
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

    private fun setupVacancies() {
        binding.vacanciesRv.adapter = adapter
        binding.vacanciesRv.layoutManager = LinearLayoutManager(requireContext())
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
            binding.searchEditText.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_search, 0)
            binding.clearButton.isEnabled = false
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
