package com.example.skirental.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.skirental.databinding.DetailsEquipmentFragmentBinding
import com.example.skirental.viewmodels.DetailsEquipmentViewModel

class DetailsEquipmentFragment : Fragment() {

    private lateinit var viewModel: DetailsEquipmentViewModel
    private lateinit var binding: DetailsEquipmentFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[DetailsEquipmentViewModel::class.java]
        binding = DetailsEquipmentFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }
}