package com.example.skirental.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.skirental.R
import com.example.skirental.databinding.RentChooseTypeOfRentFragmentBinding
import com.example.skirental.viewmodels.RentChooseTypeOfRentViewModel

class RentChooseTypeOfRentFragment : Fragment() {

    private lateinit var viewModel: RentChooseTypeOfRentViewModel
    private lateinit var binding: RentChooseTypeOfRentFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[RentChooseTypeOfRentViewModel::class.java]
        binding = RentChooseTypeOfRentFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setupObservers()

        return binding.root
    }

    private fun setupObservers() {
        viewModel.onNavigateToCalendar.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(R.id.action_rentChooseTypeOfRentFragment_to_calendarFragment)
        })

        viewModel.onChooseEquipmentClicked.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(R.id.action_rentChooseTypeOfRentFragment_to_equipmentFirstFragment)
        })
    }
}