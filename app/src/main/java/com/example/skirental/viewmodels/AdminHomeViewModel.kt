package com.example.skirental.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.skirental.utils.SingleLiveEvent

class AdminHomeViewModel : ViewModel() {

    private val _onAddEquipmentClicked = SingleLiveEvent<Void>()
    val onAddEquipmentClicked : LiveData<Void> = _onAddEquipmentClicked

    private val _onSignOutClicked = SingleLiveEvent<Void>()
    val onSignOutClicked : LiveData<Void> = _onSignOutClicked


    fun onAddEquipmentClicked() {
        _onAddEquipmentClicked.call()
    }

    fun onSignOutClicked() {
        _onSignOutClicked.call()
    }

}