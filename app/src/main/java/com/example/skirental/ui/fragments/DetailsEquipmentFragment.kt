package com.example.skirental.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.skirental.R
import com.example.skirental.databinding.CalendarFragmentBinding
import com.example.skirental.databinding.FragmentDetailsEquipmentBinding
import com.example.skirental.viewmodels.CalendarViewModel
import com.example.skirental.viewmodels.DetailsEquipmentViewModel

class DetailsEquipmentFragment : Fragment() {

    private lateinit var viewModel: DetailsEquipmentViewModel
    private lateinit var binding: FragmentDetailsEquipmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[DetailsEquipmentViewModel::class.java]
        binding = FragmentDetailsEquipmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }
}