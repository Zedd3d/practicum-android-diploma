package ru.practicum.android.diploma.presentation.filters.region.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.filters.models.FilterValue

class FiltersAreaAdapter(
    private val listFilterValues: List<FilterValue>,
    private val clickListener: (filterValue: FilterValue) -> Unit
) : RecyclerView.Adapter<FiltersAreaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FiltersAreaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.filter_area_item, parent, false)

        return FiltersAreaViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listFilterValues.size
    }

    override fun onBindViewHolder(holder: FiltersAreaViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            clickListener.invoke(listFilterValues[position])
        }
        val item = listFilterValues[position]
        holder.bind(item)
    }
}
