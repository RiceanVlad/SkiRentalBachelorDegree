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
import com.example.skirental.databinding.RentChoosePersonFragmentBinding
import com.example.skirental.viewmodels.RentChoosePersonViewModel

class RentChoosePersonFragment : Fragment() {

    private lateinit var viewModel: RentChoosePersonViewModel
    private lateinit var binding: RentChoosePersonFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[RentChoosePersonViewModel::class.java]
        binding = RentChoosePersonFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setupObservers()

        return binding.root
    }

    private fun setupObservers() {
        viewModel.onRentForMeClicked.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(R.id.action_rentChoosePersonFragment_to_rentChooseTypeOfRentFragment)
        })

        viewModel.onRentForSomeoneElseClicked.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(R.id.action_rentChoosePersonFragment_to_rentDetailsAnotherPersonFragment)
        })
    }
}