package com.example.skirental.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.skirental.viewmodels.LoginDetailsViewModel
import com.example.skirental.R
import com.example.skirental.databinding.LoginDetailsFragmentBinding

class LoginDetailsFragment : Fragment() {

    private lateinit var viewModel: LoginDetailsViewModel
    private lateinit var binding: LoginDetailsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[LoginDetailsViewModel::class.java]
        binding = LoginDetailsFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setupSpinnerAdapters()

        return binding.root
    }

    private fun setupSpinnerAdapters() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.height_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerHeight.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.weight_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerWeight.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.footsize_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerFootsize.adapter = adapter
        }
    }
}