package com.example.skirental.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.skirental.databinding.EquipmentFirstFragmentBinding
import com.example.skirental.viewmodels.EquipmentFirstViewModel

class EquipmentFirstFragment : Fragment() {

    private lateinit var viewModel: EquipmentFirstViewModel
    private lateinit var binding: EquipmentFirstFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[EquipmentFirstViewModel::class.java]
        binding = EquipmentFirstFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }
}