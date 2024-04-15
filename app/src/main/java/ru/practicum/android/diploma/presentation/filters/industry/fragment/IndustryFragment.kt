package ru.practicum.android.diploma.presentation.filters.industry.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.app.App
import ru.practicum.android.diploma.databinding.FragmentFilterIndustryBinding
import ru.practicum.android.diploma.presentation.Factory
import ru.practicum.android.diploma.presentation.filters.industry.state.FiltersIndustriesState
import ru.practicum.android.diploma.presentation.filters.industry.viewmodel.IndustryViewModel


class IndustryFragment : Fragment() {
    private var _binding: FragmentFilterIndustryBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<IndustryViewModel> {
        Factory {
            (requireContext().applicationContext as App).appComponent.generalComponent().viewModel()
        }
    }
    private var selectedIndustryId: String? = null
    private var selectedIndustryIndex: Int = -1
    private var adapter = IndustriesAdapter { industry ->
        binding.chooseIndustryButton.isVisible = industry.active
        viewModel.currentIndustry = industry.industry
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFilterIndustryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getIndustries()
        binding.industryList.adapter = this.adapter
        binding.choosingIndustryEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.filterIndustries(text.toString())
            if (text.isNullOrBlank()) {
                binding.clearButton.visibility = View.GONE
                binding.searchDrawable.visibility = View.VISIBLE
                if (adapter.checkedRadioButtonId != -1) {
                    binding.chooseIndustryButton.visibility = View.VISIBLE
                }
            } else {
                binding.clearButton.visibility = View.VISIBLE
                binding.searchDrawable.visibility = View.GONE
                binding.clearButton.setOnClickListener {
                    binding.choosingIndustryEditText.text?.clear()
                }
            }
        }

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.chooseIndustryButton.setOnClickListener {
            parentFragmentManager.setFragmentResult(REQUEST_KEY, bundleOf(INDUSTRY_KEY to viewModel.currentIndustry))
            findNavController().popBackStack()
        }

        viewModel.industriesState.observe(viewLifecycleOwner) { state ->
            when (state) {
                FiltersIndustriesState.Error -> {
                }

                FiltersIndustriesState.Empty -> {
                    showEmpty()
                }

                FiltersIndustriesState.Initial -> Unit
                FiltersIndustriesState.Loading -> {
                    showLoading()
                }

                is FiltersIndustriesState.Success -> {
                    showContent(state.data)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showContent(data: List<IndustriesAdapterItem>) {
        val active = data.find { it.industry.id == viewModel.currentIndustry?.id }
        if (active == null) {
            binding.chooseIndustryButton.isVisible = false
        } else {
            active.active = true
            binding.chooseIndustryButton.isVisible = true
        }
        adapter.updateList(data)

        if (viewModel.currentIndustry == null) {
            val industryIdPrefs = arguments?.getString(INDUSTRY_KEY_ID)
            adapter.setSelectedIndustry(industryIdPrefs)
            selectedIndustryId = industryIdPrefs
            selectedIndustryIndex = adapter.checkedRadioButtonId

            if (!industryIdPrefs.isNullOrEmpty()) {
                val position = adapter.data.indexOfFirst { it.industry.id == industryIdPrefs }
                if (position != -1) {
                    binding.chooseIndustryButton.visibility = View.VISIBLE
                    viewModel.currentIndustry = adapter.data[position].industry
                }
            }
        }

        binding.industryList.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }

    private fun showEmpty() {
        binding.chooseIndustryButton.visibility = View.GONE
        binding.industryList.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.chooseIndustryButton.visibility = View.GONE
        binding.industryList.visibility = View.GONE
    }

    companion object {
        const val REQUEST_KEY = "KEY"
        const val INDUSTRY_KEY = "INDUSTRY"
        const val INDUSTRY_KEY_ID = "INDUSTRY_ID"
    }
}
