package com.example.skirental.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.skirental.viewmodels.WeatherViewModel
import com.example.skirental.databinding.WeatherFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherFragment : Fragment() {

    private lateinit var binding: WeatherFragmentBinding
    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = WeatherFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        setupWeather()
        binding.isLoading = true

        return binding.root
    }

    private fun setupWeather() {
        viewModel.weatherResponse.observe(viewLifecycleOwner, Observer { weather ->
            binding.isLoading = false
            binding.apply {
                tvCityName.text = "Muntele Mic"
                tvDescription.text = weather.description
                tvTemperature.text = weather.temperature
                tvWind.text = weather.wind

                val forecast = weather.forecast
                tvForecast1.text = "${forecast[0].temperature}/ ${forecast[0].wind}"
                tvForecast2.text = "${forecast[1].temperature}/ ${forecast[1].wind}"
                tvForecast3.text = "${forecast[2].temperature}/ ${forecast[2].wind}"

            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.lifecycleOwner = null
    }
}