package ru.practicum.android.diploma.presentation.filters.main.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.App
import ru.practicum.android.diploma.databinding.FilterCategotyElementBinding
import ru.practicum.android.diploma.databinding.FragmentFiltersMainBinding
import ru.practicum.android.diploma.presentation.Factory
import ru.practicum.android.diploma.presentation.filters.main.state.FiltersMainViewState
import ru.practicum.android.diploma.presentation.filters.main.view_model.FiltersMainViewModel
import ru.practicum.android.diploma.util.onTextChange


class FiltersMainFragment : Fragment(R.layout.fragment_filters_main) {

    private val viewModel by viewModels<FiltersMainViewModel> {
        Factory {
            App.appComponent.generalComponent().viewModel()
        }
    }

    private val binding by viewBinding(FragmentFiltersMainBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.llWorkPlace.smallTextBlock.setText(getString(R.string.filter_workplace))
        binding.llIndustries.smallTextBlock.setText(getString(R.string.filter_industries))

        binding.tietSalary.onTextChange {
            setHintTextColor(it)
        }

        onChangeViewState(FiltersMainViewState.Empty)

    }

    @SuppressLint("ResourceAsColor")
    private fun setHintTextColor(salaryText: String) {
        if (salaryText.isNotBlank()) {
            binding.tvSalaryHint.setTextColor(R.color.Blue_yp)
        } else {
            binding.tvSalaryHint.setTextColor(R.color.Gray_yp)
        }
    }


    private fun onChangeViewState(state: FiltersMainViewState) {
        when (state) {
            is FiltersMainViewState.Empty -> {
                setViewPropertys(binding.llWorkPlace, "")
                setViewPropertys(binding.llIndustries, "")
            }

            is FiltersMainViewState.Content -> {
                setViewPropertys(binding.llWorkPlace, state.workPlace)
                setViewPropertys(binding.llIndustries, state.Industries)
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun setViewPropertys(v: FilterCategotyElementBinding, textValue: String) {
        if (textValue.isEmpty()) {
            v.smallTextBlock.isVisible = false
            v.standardTextBlock.text = v.smallTextBlock.text
            v.standardTextBlock.setTextColor(R.color.Gray_yp)
        } else {
            v.smallTextBlock.isVisible = true
            v.standardTextBlock.text = textValue
            v.standardTextBlock.setTextColor(R.color.BlackDay_WhiteNight)
        }

    }
}
