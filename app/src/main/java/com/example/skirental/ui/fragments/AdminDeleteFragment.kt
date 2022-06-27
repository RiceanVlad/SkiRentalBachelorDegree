package com.example.skirental.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.skirental.R
import com.example.skirental.databinding.FragmentAdminDeleteBinding
import com.example.skirental.databinding.FragmentAdminHomeBinding
import com.example.skirental.databinding.LoginDetailsFragmentBinding
import com.example.skirental.viewmodelfactories.AdminDeleteViewModelFactory
import com.example.skirental.viewmodelfactories.LoginDetailsViewModelFactory
import com.example.skirental.viewmodels.AdminDeleteViewModel
import com.example.skirental.viewmodels.LoginDetailsViewModel

class AdminDeleteFragment : Fragment() {

    private lateinit var viewModel: AdminDeleteViewModel
    private lateinit var binding: FragmentAdminDeleteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModelFactory = AdminDeleteViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[AdminDeleteViewModel::class.java]
        binding = FragmentAdminDeleteBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }

}