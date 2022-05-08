package com.example.skirental.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.skirental.adapters.EquipmentAdapter
import com.example.skirental.databinding.EquipmentFragmentBinding
import com.example.skirental.models.Equipment
import com.example.skirental.utils.State
import com.example.skirental.viewmodelfactories.EquipmentViewModelFactory
import com.example.skirental.viewmodels.EquipmentViewModel
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
        binding.equipmentList.adapter = adapter
        setupFlows()

        return  binding.root
    }

    private fun setupFlows() {
        lifecycleScope.launch {
            loadEquipments()
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.addEquipmentClickedSharedFlow.collect {
                    addEquipment(Equipment("", "skis1", 95, 150, 42))
                }
            }
        }
    }

    private suspend fun loadEquipments() {
        viewModel.getAllEquipments().collect() { state ->
            when(state) {
                is State.Loading -> {
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                }
                is State.Success -> {
                    adapter.submitList(state.data)
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
//                    binding.buttonAdd.isEnabled = false
                }

                is State.Success -> {
                    Toast.makeText(requireContext(), "Added", Toast.LENGTH_SHORT).show()
//                    binding.buttonAdd.isEnabled = true
                }

                is State.Failed -> {
                    Toast.makeText(requireContext(), "Failed! ${state.message}", Toast.LENGTH_SHORT).show()
//                    binding.buttonAdd.isEnabled = true
                }
            }
        }
    }
}