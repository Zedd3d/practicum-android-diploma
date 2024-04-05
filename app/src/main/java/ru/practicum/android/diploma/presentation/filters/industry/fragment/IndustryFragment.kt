package ru.practicum.android.diploma.presentation.filters.industry.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.databinding.FragmentFilterIndustryBinding

class IndustryFragment: Fragment() {
    private var _industryBinding : FragmentFilterIndustryBinding? = null
    private val industryBinding get() = _industryBinding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _industryBinding = FragmentFilterIndustryBinding.inflate(inflater, container, false)
        return industryBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        industryBinding.toolbarText.setOnClickListener {
            findNavController().popBackStack()
        }
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _industryBinding = null
    }

}
