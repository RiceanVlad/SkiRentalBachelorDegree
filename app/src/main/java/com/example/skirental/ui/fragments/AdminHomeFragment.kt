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
import com.example.skirental.databinding.AccountFragmentBinding
import com.example.skirental.databinding.FragmentAdminHomeBinding
import com.example.skirental.viewmodels.AccountViewModel
import com.example.skirental.viewmodels.AdminHomeViewModel

class AdminHomeFragment : Fragment() {

    private lateinit var viewModel: AdminHomeViewModel
    private lateinit var binding: FragmentAdminHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[AdminHomeViewModel::class.java]
        binding = FragmentAdminHomeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setupObservers()

        return binding.root
    }

    private fun setupObservers() {
        viewModel.onAddEquipmentClicked.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(AdminHomeFragmentDirections.actionAdminHomeFragmentToAdminAddEquipmentFragment())
        })
    }

}