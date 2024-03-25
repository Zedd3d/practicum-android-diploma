package ru.practicum.android.diploma.presentation.general

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.App
import ru.practicum.android.diploma.base.Factory
import ru.practicum.android.diploma.databinding.FragmentGeneralBinding

class GeneralFragment : Fragment(R.layout.fragment_general) {

    private val viewModel by viewModels<GeneralViewModel> {
        Factory{
            App.appComponent.generalComponent().viewModel()
        }
    }

    private val binding by viewBinding(FragmentGeneralBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}
