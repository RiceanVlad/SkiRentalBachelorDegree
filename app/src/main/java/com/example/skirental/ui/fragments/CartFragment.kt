package com.example.skirental.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.skirental.R
import com.example.skirental.databinding.CalendarFragmentBinding
import com.example.skirental.databinding.CartFragmentBinding
import com.example.skirental.viewmodels.CalendarViewModel
import com.example.skirental.viewmodels.CartViewModel

class CartFragment : Fragment() {

    private lateinit var viewModel: CartViewModel
    private lateinit var binding: CartFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[CartViewModel::class.java]
        binding = CartFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return  binding.root
    }
}