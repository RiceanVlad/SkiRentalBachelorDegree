package com.example.skirental.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skirental.models.Weather
import com.example.skirental.repositories.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel
@Inject
constructor(private val repository: WeatherRepository) : ViewModel() {

    private val _response = MutableLiveData<Weather>()
    val weatherResponse: LiveData<Weather>
        get() = _response


    init {
        getWeather()
    }

    private fun getWeather() = viewModelScope.launch {
        repository.getWeather().let { response ->

            if (response.isSuccessful) {
                _response.postValue(response.body())
            } else {
                Timber.d("getWeather Error: " + response.code())
            }
        }
    }


}













