package com.example.skirental.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.skirental.databinding.WeatherFragmentBinding
import com.example.skirental.viewmodels.WeatherViewModel

class WeatherFragment : Fragment() {

    private lateinit var viewModel: WeatherViewModel
    private lateinit var binding: WeatherFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
        binding = WeatherFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }
}