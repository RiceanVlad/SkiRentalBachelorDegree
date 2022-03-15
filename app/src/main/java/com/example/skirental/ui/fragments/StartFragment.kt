package com.example.skirental.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.skirental.R
import com.example.skirental.databinding.StartFragmentBinding
import com.example.skirental.viewmodels.StartViewModel

class StartFragment : Fragment() {

    private lateinit var viewModel: StartViewModel
    private lateinit var binding: StartFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[StartViewModel::class.java]
        binding = StartFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.btnNavigateToDetails.setOnClickListener {
            findNavController().navigate(StartFragmentDirections.actionStartFragmentToLoginDetailsFragment())
        }

        return binding.root
    }
}