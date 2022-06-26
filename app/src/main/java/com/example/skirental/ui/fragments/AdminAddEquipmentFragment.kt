package com.example.skirental.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.skirental.R
import com.example.skirental.databinding.FragmentAdminAddEquipmentBinding
import com.example.skirental.databinding.StartFragmentBinding
import com.example.skirental.enums.EquipmentType
import com.example.skirental.viewmodels.AdminAddEquipmentViewModel
import com.example.skirental.viewmodels.StartViewModel

class AdminAddEquipmentFragment : Fragment() {

    private lateinit var viewModel: AdminAddEquipmentViewModel
    private lateinit var binding: FragmentAdminAddEquipmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[AdminAddEquipmentViewModel::class.java]
        binding = FragmentAdminAddEquipmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setupSpinnerAdapters()

        return binding.root
    }

    private fun setupSpinnerAdapters() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.equipment_type_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            val equipmentTypeArray = resources.getStringArray(R.array.equipment_type_array)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerAddEquipmentType.adapter = adapter
            binding.spinnerAddEquipmentType.onItemSelectedListener = object :
                AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                    val equipmentType = equipmentTypeArray[pos]
                    binding.equipmentType = EquipmentType.valueOf(equipmentType)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemClick(p0: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                }
            }
        }
    }
}