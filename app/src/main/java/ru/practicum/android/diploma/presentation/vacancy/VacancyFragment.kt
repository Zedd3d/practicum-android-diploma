package ru.practicum.android.diploma.presentation.vacancy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import io.noties.markwon.Markwon
import io.noties.markwon.html.HtmlPlugin
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.App
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.presentation.Factory
import ru.practicum.android.diploma.util.SalaryUtil


class VacancyFragment : Fragment(R.layout.fragment_vacancy) {

    companion object {
        private const val RADIUS = 8
    }

    private val vacancyId: String? by lazy { requireArguments().getString("id") }

    private var _binding: FragmentVacancyBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<VacancyViewModel> {
        Factory {
            (requireContext().applicationContext as App).appComponent.vacancyComponent()
                .create(requireNotNull(vacancyId)).viewModel()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentVacancyBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonAddToFavorites.setOnClickListener {
            viewModel.clickFavorite()
        }

        binding.vacancyToolbars.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.buttonShare.setOnClickListener {
            viewModel.shareVacancy()
        }

        viewModel.observeUi().observe(viewLifecycleOwner) { state ->
            render(state)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun render(state: ViewState) {
        val vacancy = state.vacancy

        binding.jobName.text = vacancy?.name ?: ""
        vacancy?.salary?.let {
            binding.jobSalary.text = SalaryUtil.formatSalary(requireContext(), vacancy.salary)
        }
        binding.companyName.text = vacancy?.area ?: ""
        binding.neededExperience.text = vacancy?.experience ?: ""
        binding.jobTime.text = vacancy?.employment ?: ""
        vacancy?.employer?.let {
            Glide.with(requireContext())
                .load(vacancy.employer.logoUrls) // false
                .placeholder(R.drawable.placeholder_company_icon)
                .fitCenter()
                .transform(RoundedCorners(RADIUS))
                .into(binding.ivCompany)
        }

        vacancy?.description?.let {
            val markwon = Markwon.builder(requireContext())
                .usePlugin(HtmlPlugin.create())
                .build()
            markwon.setMarkdown(binding.vacancyDescription, it)
        }

        if (state.isFavorite) {
            binding.buttonAddToFavorites.setImageResource(R.drawable.favorite_vacancy_drawable_fill)
        } else {
            binding.buttonAddToFavorites.setImageResource(R.drawable.favorite_vacancy_drawable_empty)
        }
    }
}
