package com.example.skirental.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.skirental.utils.SingleLiveEvent

class RentChoosePersonViewModel : ViewModel() {
    private val _onRentForMeClicked = SingleLiveEvent<Void>()
    val onRentForMeClicked : LiveData<Void> = _onRentForMeClicked

    private val _onRentForSomeoneElseClicked = SingleLiveEvent<Void>()
    val onRentForSomeoneElseClicked : LiveData<Void> = _onRentForSomeoneElseClicked

    fun onRentForMeClicked() {
        _onRentForMeClicked.call()
    }

    fun onRentForSomeoneElseClicked() {
        _onRentForSomeoneElseClicked.call()
    }
}