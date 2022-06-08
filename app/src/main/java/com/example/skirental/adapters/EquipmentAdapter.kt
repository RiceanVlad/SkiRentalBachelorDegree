package com.example.skirental.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import com.example.skirental.databinding.ListItemEquipmentBinding
import com.example.skirental.enums.FilterType
import com.example.skirental.models.Equipment

class EquipmentAdapter(private var equipmentList:MutableList<Equipment>, val clickListener: EquipmentListener) :
    ListAdapter<Equipment, EquipmentAdapter.ViewHolder>(EquipmentDiffCallback()), Filterable{

    var filteredEquipmentList = equipmentList

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ListItemEquipmentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Equipment, clickListener: EquipmentListener) {
            binding.equipment = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemEquipmentBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    fun updateList(newEquipmentList: MutableList<Equipment>) {
        equipmentList = newEquipmentList
    }

    override fun getFilter(): Filter { // ignore this function (look below)
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {

                val charString = charSequence.toString().lowercase()

                if(charString.isEmpty()) {
                    filteredEquipmentList = equipmentList
                } else {
                    val filteredList = equipmentList
                        .filter { (it.description.lowercase() + " " + it.length.toString() + " size " + it.shoeSize.toString()).contains(charString) }
                        .toMutableList()

                    filteredEquipmentList = filteredList
                }

                val filterResults = Filter.FilterResults()
                filterResults.values = filteredEquipmentList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
                submitList(filterResults.values as MutableList<Equipment>)
                notifyDataSetChanged()
            }
        }
    }

    fun getFilter(filterType: FilterType): Filter { // use this filter function
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {

                val charString = charSequence.toString().lowercase()
                when(filterType) {
                    FilterType.RESET -> {
                        filteredEquipmentList = if(charString.isEmpty()) {
                            equipmentList
                        } else {
                            val filteredList = equipmentList
                                .filter { (it.description.lowercase() + " " + it.length.toString() + " size " + it.shoeSize.toString()).contains(charString) }
                                .toMutableList()

                            filteredList
                        }
                    }
                    FilterType.PERSONAL_DETAILS -> {
                        filteredEquipmentList = if(charString.isEmpty()) {
                            equipmentList
                        } else {
                            val filteredList = equipmentList
                                .filter { (charString.contains(it.length.toString())) }
                                .toMutableList()

                            filteredList
                        }
                    }
                    FilterType.CUSTOM -> {
                        filteredEquipmentList = if(charString.isEmpty()) {
                            equipmentList
                        } else {
                            val filteredList = equipmentList
                                .filter { (it.description.lowercase() + " " + it.length.toString() + " size " + it.shoeSize.toString()).contains(charString) }
                                .toMutableList()

                            filteredList
                        }
                    }
                }

                val filterResults = Filter.FilterResults()
                filterResults.values = filteredEquipmentList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
                submitList(filterResults.values as MutableList<Equipment>)
                notifyDataSetChanged()
            }
        }
    }

}

class EquipmentDiffCallback : DiffUtil.ItemCallback<Equipment>() {

    override fun areItemsTheSame(oldItem: Equipment, newItem: Equipment): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Equipment, newItem: Equipment): Boolean {
        return oldItem == newItem
    }
}

class EquipmentListener(val clickListener: (equipment: Equipment) -> Unit) {
    fun onClick(equipment: Equipment) = clickListener(equipment)
}