package com.example.skirental.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.skirental.R
import com.example.skirental.databinding.FragmentAdminAddEquipmentBinding
import com.example.skirental.databinding.StartFragmentBinding
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

        return binding.root
    }

}