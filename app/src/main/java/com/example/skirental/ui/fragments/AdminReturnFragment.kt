package com.example.skirental.ui.fragments

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.skirental.R
import com.example.skirental.databinding.EquipmentFragmentBinding
import com.example.skirental.databinding.FragmentAdminReturnBinding
import com.example.skirental.viewmodels.AdminReturnViewModel
import com.example.skirental.viewmodels.EquipmentViewModel
import com.google.zxing.integration.android.IntentIntegrator

class AdminReturnFragment : Fragment() {

    private lateinit var viewModel: AdminReturnViewModel
    private lateinit var binding: FragmentAdminReturnBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[AdminReturnViewModel::class.java]
        binding = FragmentAdminReturnBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setupObservers()

        return binding.root
    }

    private fun setupObservers() {
        viewModel.onScanEquipmentClicked.observe(viewLifecycleOwner, Observer {
            val scanner = IntentIntegrator(requireActivity())
            scanner.initiateScan()
        })
    }

}