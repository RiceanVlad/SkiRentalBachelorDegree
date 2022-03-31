package com.example.skirental.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.skirental.R
import com.example.skirental.databinding.RentChoosePersonFragmentBinding
import com.example.skirental.databinding.RentDetailsAnotherPersonFragmentBinding
import com.example.skirental.viewmodels.RentChoosePersonViewModel
import com.example.skirental.viewmodels.RentDetailsAnotherPersonViewModel

class RentDetailsAnotherPersonFragment : Fragment() {

    private lateinit var viewModel: RentDetailsAnotherPersonViewModel
    private lateinit var binding: RentDetailsAnotherPersonFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[RentDetailsAnotherPersonViewModel::class.java]
        binding = RentDetailsAnotherPersonFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }
}