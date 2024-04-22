package ru.practicum.android.diploma.presentation.filters.industry.fragment

import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ListOfIndustryBinding

class IndustryViewHolder(val binding: ListOfIndustryBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: IndustriesAdapterItem) {
        binding.department.text = item.industry.name
        binding.radioButton.isChecked = item.active
    }
}
