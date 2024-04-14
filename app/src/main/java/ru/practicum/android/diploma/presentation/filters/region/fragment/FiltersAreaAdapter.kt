package ru.practicum.android.diploma.presentation.filters.region.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Area

class FiltersAreaAdapter(
    private var listAreas: List<Area>,
    private val clickListener: (filterValue: Area) -> Unit
) : RecyclerView.Adapter<FiltersAreaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FiltersAreaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.filter_area_item, parent, false)

        return FiltersAreaViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listAreas.size
    }

    fun setNewList(list: List<Area>) {
        listAreas = list
    }

    override fun onBindViewHolder(holder: FiltersAreaViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            clickListener.invoke(listAreas[position])
        }
        val item = listAreas[position]
        holder.bind(item)
    }
}
