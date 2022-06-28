package com.example.skirental.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.example.skirental.R
import com.example.skirental.adapters.EquipmentAdapter
import com.example.skirental.adapters.EquipmentListener
import com.example.skirental.databinding.FragmentAdminDeleteBinding
import com.example.skirental.enums.EquipmentType
import com.example.skirental.models.Equipment
import com.example.skirental.utils.State
import com.example.skirental.viewmodelfactories.AdminDeleteViewModelFactory
import com.example.skirental.viewmodels.AdminDeleteViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AdminDeleteFragment : Fragment() {

    private lateinit var viewModel: AdminDeleteViewModel
    private lateinit var binding: FragmentAdminDeleteBinding
    private lateinit var adapter: EquipmentAdapter
    private var equipmentType = EquipmentType.SKI

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModelFactory = AdminDeleteViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[AdminDeleteViewModel::class.java]
        binding = FragmentAdminDeleteBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        adapter = EquipmentAdapter(mutableListOf<Equipment>(), EquipmentListener { equipment ->
            createDeleteEquipmentAlert(equipment)
        })
        binding.rvDeleteEquipment.adapter = adapter
        setupSpinnerAdapters()
        loadEquipmentsBasedOnType()

        return binding.root
    }

    private fun loadEquipmentsBasedOnType() {
        lifecycleScope.launch {
            loadEquipments()
        }
    }

    private fun setupSpinnerAdapters() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.equipment_type_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            val equipmentTypeArray = resources.getStringArray(R.array.equipment_type_array)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerDeleteEquipmentType.adapter = adapter
            binding.spinnerDeleteEquipmentType.onItemSelectedListener = object :
                AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                    val equipType = equipmentTypeArray[pos]
                    equipmentType = EquipmentType.valueOf(equipType)
                    loadEquipmentsBasedOnType()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemClick(p0: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                }
            }
        }
    }

    private fun createDeleteEquipmentAlert(equipment: Equipment): AlertDialog {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Delete item")
        builder.setMessage("Are you sure you want to delete this item?")
        builder.setCancelable(false)
        builder.setPositiveButton("Yes") {
                dialog, _ -> dialog.cancel()
                lifecycleScope.launch {
                    onDeleteEquipment(equipment)
                }
        }
        builder.setNegativeButton("No") { dialog, _ -> dialog.cancel() }
        return builder.show()
    }

    private suspend fun onDeleteEquipment(equipment: Equipment) {
        viewModel.deleteEquipment(equipment).collect() { state ->
            when(state) {
                is State.Loading -> {
                    binding.isLoading = true
                }
                is State.Success -> {
                    Toast.makeText(requireContext(), "Item deleted", Toast.LENGTH_SHORT).show()
                    loadEquipmentsBasedOnType()
                    binding.isLoading = false

                }
                is State.Failed -> {
                    Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show()
                    binding.isLoading = false

                }
            }
        }
    }

    private suspend fun loadEquipments() {
        viewModel.getAllEquipments(equipmentType).collect() { state ->
            when(state) {
                is State.Loading -> {
                    binding.isLoading = true
                }
                is State.Success -> {
                    adapter.updateList(state.data as MutableList<Equipment>)
                    adapter.submitList(state.data)
                    binding.isLoading = false

                }
                is State.Failed -> {
                    Toast.makeText(requireContext(), "Failed! ${state.message}", Toast.LENGTH_SHORT).show()
                    binding.isLoading = false

                }


            }
        }
    }
}