package com.example.skirental.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.skirental.databinding.DetailsEquipmentFragmentBinding
import com.example.skirental.viewmodels.DetailsEquipmentViewModel

class DetailsEquipmentFragment : Fragment() {

    private lateinit var viewModel: DetailsEquipmentViewModel
    private lateinit var binding: DetailsEquipmentFragmentBinding
    private val args: DetailsEquipmentFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[DetailsEquipmentViewModel::class.java]
        binding = DetailsEquipmentFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        Toast.makeText(requireContext(), "${args.equipment.id} + ${args.equipment.type}", Toast.LENGTH_SHORT).show()

        return binding.root
    }
}