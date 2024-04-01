package ru.practicum.android.diploma.presentation.general.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.App
import ru.practicum.android.diploma.presentation.Factory
import ru.practicum.android.diploma.databinding.FragmentGeneralBinding
import ru.practicum.android.diploma.presentation.general.view_model.GeneralViewModel
import ru.practicum.android.diploma.presentation.general.view_model.ResponseState
import ru.practicum.android.diploma.presentation.general.VacanciesAdapter
import ru.practicum.android.diploma.util.onTextChangeDebounce
import ru.practicum.android.diploma.util.visibleOrGone

class GeneralFragment : Fragment(R.layout.fragment_general) {

    private val viewModel by viewModels<GeneralViewModel> {
        Factory{
            App.appComponent.generalComponent().viewModel()
        }
    }

    private val binding by viewBinding(FragmentGeneralBinding::bind)
    private lateinit var adapter: VacanciesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupVacancies()
        binding.searchEditText.onTextChangeDebounce()
            .debounce(2000)
            .onEach {
                val query = it?.toString().orEmpty()
                viewModel.search(query)
                if (query.isNotEmpty()){
                    binding.vacanciesLoading.visibleOrGone(true)
                    binding.vacanciesRv.visibleOrGone(false)
                }
            }
            .launchIn(lifecycleScope)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.observeUi().collect{state ->
                    adapter.submitList(state.vacancies)
                    updateStatus(state.status)
                    binding.vacanciesProgress.visibleOrGone(false)
                    binding.vacanciesLoading.visibleOrGone(false)
                    binding.foundCountText.text = getString(R.string.found_count, state.found.toString())
                }
            }

        }

        binding.vacanciesRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) {
                    val pos = (binding.vacanciesRv.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    val itemsCount = adapter.itemCount
                    if (pos >= itemsCount-1) {
                        viewModel.onLastItemReached(binding.searchEditText.text.toString())
                        binding.vacanciesProgress.visibleOrGone(true)
                    }
                }
            }
        })
    }

    private fun updateStatus(status: ResponseState) {
        binding.vacanciesRv.visibleOrGone(status == ResponseState.Content)
        binding.src.visibleOrGone(status != ResponseState.Content)
        binding.srcText.visibleOrGone(status != ResponseState.Content)
        binding.foundCount.visibleOrGone(status == ResponseState.Content)
        when(status){
            ResponseState.Empty -> {
                binding.srcText.setText(R.string.no_vacancies)
            }
            ResponseState.ServerError -> {
                binding.srcText.setText(R.string.server_error)
            }
            ResponseState.NetworkError -> {
                binding.srcText.setText(R.string.no_internet)
            }
            else -> {binding.srcText.text = ""}
        }
        val image = when(status){
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
            else -> {null}
        }

        image?.let {
            Glide.with(requireContext())
                .load(image)
                .into(binding.src)
        }
    }

    private fun setupVacancies() {
        adapter = VacanciesAdapter()
        binding.vacanciesRv.adapter = adapter
        binding.vacanciesRv.layoutManager = LinearLayoutManager(requireContext())
    }

}
