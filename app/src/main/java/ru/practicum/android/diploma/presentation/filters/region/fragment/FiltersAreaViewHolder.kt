package ru.practicum.android.diploma.presentation.filters.region.fragment

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.filters.models.FilterValue

class FiltersAreaViewHolder(parentView: View) : RecyclerView.ViewHolder(parentView) {
    private val filterDescription: TextView

    init {
        filterDescription = parentView.findViewById(R.id.filterDescription)
    }

    fun bind(filterValue: FilterValue) {
        filterDescription.text = filterValue.name
    }
}
