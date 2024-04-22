package ru.practicum.android.diploma.presentation.filters.industry.fragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ListOfIndustryBinding
import ru.practicum.android.diploma.domain.filters.industry.models.SubIndustry

class IndustriesAdapter(private val onClick: (IndustriesAdapterItem) -> Unit) :
    RecyclerView.Adapter<IndustryViewHolder>() {
    var data: List<IndustriesAdapterItem> = emptyList()
    var checkedRadioButtonId: Int = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndustryViewHolder {
        return IndustryViewHolder(ListOfIndustryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: IndustryViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
        holder.binding.root.setOnClickListener {
            updateSelectedIndustry(position)
        }
        holder.binding.radioButton.setOnClickListener {
            updateSelectedIndustry(position)
        }
    }

    private fun updateSelectedIndustry(position: Int) {
        checkedRadioButtonId = position
        data[position].active = true
        onClick.invoke(data[position])
        notifyItemChanged(position)
        val oldPosition = data.indexOfFirst { it != data[position] && it.active }
        if (oldPosition > -1) {
            data[oldPosition].active = false
            notifyItemChanged(oldPosition)
        }
    }

    fun updateList(list: List<IndustriesAdapterItem>) {
        data = list
        notifyDataSetChanged()
    }
}

class IndustriesAdapterItem(
    val industry: SubIndustry,
    var active: Boolean = false
)
