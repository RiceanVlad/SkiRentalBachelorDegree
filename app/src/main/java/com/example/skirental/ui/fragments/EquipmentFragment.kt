package com.example.skirental.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.skirental.adapters.EquipmentAdapter
import com.example.skirental.databinding.EquipmentFragmentBinding
import com.example.skirental.models.Equipment
import com.example.skirental.utils.State
import com.example.skirental.viewmodelfactories.EquipmentViewModelFactory
import com.example.skirental.viewmodels.EquipmentViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class EquipmentFragment : Fragment() {

    private lateinit var viewModel: EquipmentViewModel
    private lateinit var binding: EquipmentFragmentBinding
    private lateinit var adapter: EquipmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModelFactory = EquipmentViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[EquipmentViewModel::class.java]
        binding = EquipmentFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        adapter = EquipmentAdapter()
//        binding.equipmentList.adapter = adapter
//        setupObservers()

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

        lifecycleScope.launch {
            loadEquipments()
        }

        return  binding.root
    }

//    private fun setupObservers() {
//        viewModel.itemsList.observe(viewLifecycleOwner, Observer { itemsList ->
//            adapter.submitList(itemsList)
//        })
//    }

    private suspend fun loadEquipments() {
        viewModel.getAllEquipments().collect() { state ->
            when(state) {
                is State.Loading -> {
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                }
                is State.Success -> {
                    val equipmentText = state.data.joinToString("\n") {
                        "${it.length} ~ ${it.description}"
                    }
                    Toast.makeText(requireContext(), "Success: $equipmentText", Toast.LENGTH_SHORT).show()
                    binding.tvEquipmentText.text = equipmentText
                }
                is State.Failed -> Toast.makeText(requireContext(), "Failed! ${state.message}", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private suspend fun addEquipment(Equipment: Equipment) {
        viewModel.addEquipment(Equipment).collect { state ->
            when (state) {
                is State.Loading -> {
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                    binding.buttonAdd.isEnabled = false
                }

                is State.Success -> {
                    Toast.makeText(requireContext(), "Added", Toast.LENGTH_SHORT).show()
//                    binding.fieldEquipmentContent.setText("")
                    binding.buttonAdd.isEnabled = true
                }

                is State.Failed -> {
                    Toast.makeText(requireContext(), "Failed! ${state.message}", Toast.LENGTH_SHORT).show()
                    binding.buttonAdd.isEnabled = true
                }
            }
        }
    }
}