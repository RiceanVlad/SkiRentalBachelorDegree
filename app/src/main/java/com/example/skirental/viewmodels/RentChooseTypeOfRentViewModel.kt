package com.example.skirental.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.skirental.utils.SingleLiveEvent

class RentChooseTypeOfRentViewModel : ViewModel() {

    private val _onNavigateToCalendar = SingleLiveEvent<Void>()
    val onNavigateToCalendar : LiveData<Void> = _onNavigateToCalendar

    fun onNavigateToCalendar() {
        _onNavigateToCalendar.call()
    }

}