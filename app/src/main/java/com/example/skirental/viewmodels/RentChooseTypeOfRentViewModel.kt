package com.example.skirental.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.skirental.utils.SingleLiveEvent

class RentChooseTypeOfRentViewModel : ViewModel() {

    private val _onNavigateToCalendar = SingleLiveEvent<Void>()
    val onNavigateToCalendar : LiveData<Void> = _onNavigateToCalendar

    private val _onChooseEquipmentClicked = SingleLiveEvent<Void>()
    val onChooseEquipmentClicked : LiveData<Void> = _onChooseEquipmentClicked

    fun onNavigateToCalendar() {
        _onNavigateToCalendar.call()
    }

    fun onChooseEquipmentClicked() {
        _onChooseEquipmentClicked.call()
    }

}