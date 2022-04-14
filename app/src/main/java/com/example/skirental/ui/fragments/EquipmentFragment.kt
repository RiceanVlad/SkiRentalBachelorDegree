package com.example.skirental.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.skirental.adapters.EquipmentAdapter
import com.example.skirental.databinding.EquipmentFragmentBinding
import com.example.skirental.models.Equipment
import com.example.skirental.viewmodels.EquipmentViewModel

class EquipmentFragment : Fragment() {

    private lateinit var viewModel: EquipmentViewModel
    private lateinit var binding: EquipmentFragmentBinding
    private lateinit var adapter: EquipmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[EquipmentViewModel::class.java]
        binding = EquipmentFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        adapter = EquipmentAdapter()
        binding.equipmentList.adapter = adapter
        setupObservers()

//        val equipmentList = listOf<Equipment>(
//            Equipment("aaa", 1, 1),
//            Equipment("bbb", 2, 2),
//            Equipment("bbb", 2, 2),
//            Equipment("bbb", 2, 2),
//            Equipment("bbb", 2, 2),
//            Equipment("bbb", 2, 2),
//            Equipment("bbb", 2, 2),
//            Equipment("bbb", 2, 2),
//            Equipment("bbb", 2, 2),
//            Equipment("bbb", 2, 2),
//            Equipment("bbb", 2, 2),
//            Equipment("bbb", 2, 2),
//            Equipment("bbb", 2, 2),
//        )
//        adapter.submitList(equipmentList)

        return  binding.root
    }

    private fun setupObservers() {
        viewModel.itemsList.observe(viewLifecycleOwner, Observer { itemsList ->
            adapter.submitList(itemsList)
        })
    }
}