package ru.practicum.android.diploma.presentation.filters.industry.fragment

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ListOfIndustryBinding
import ru.practicum.android.diploma.domain.filters.industry.models.Industry
import ru.practicum.android.diploma.domain.filters.industry.models.SubIndustry

class IndustryViewHolder(val binding: ListOfIndustryBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: SubIndustry) {
        binding.department.text = item.name
    }
}
