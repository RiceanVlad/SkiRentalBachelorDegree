package com.example.skirental.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.skirental.databinding.ModifyPersonalDetailsFragmentBinding
import com.example.skirental.viewmodels.ModifyPersonalDetailsViewModel

class ModifyPersonalDetailsFragment : Fragment() {

    private lateinit var viewModel: ModifyPersonalDetailsViewModel
    private lateinit var binding: ModifyPersonalDetailsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[ModifyPersonalDetailsViewModel::class.java]
        binding = ModifyPersonalDetailsFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }
}