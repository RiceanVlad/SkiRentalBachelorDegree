package com.example.skirental.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.skirental.utils.SingleLiveEvent

class HomeViewModel : ViewModel() {

    private val _onRentEquipmentClicked = SingleLiveEvent<Void>()
    val onRentEquipmentClicked : LiveData<Void> = _onRentEquipmentClicked

    private val _onWeatherClicked = SingleLiveEvent<Void>()
    val onWeatherClicked : LiveData<Void> = _onWeatherClicked

    fun onRentEquipmentClicked() {
        _onRentEquipmentClicked.call()
    }

    fun onWeatherClicked() {
        _onWeatherClicked.call()
    }
}