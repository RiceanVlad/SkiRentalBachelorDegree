package com.example.skirental.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.skirental.utils.SingleLiveEvent

class AdminHomeViewModel : ViewModel() {

    private val _onAddEquipmentClicked = SingleLiveEvent<Void>()
    val onAddEquipmentClicked : LiveData<Void> = _onAddEquipmentClicked

    private val _onSignOutClicked = SingleLiveEvent<Void>()
    val onSignOutClicked : LiveData<Void> = _onSignOutClicked

    private val _onDeleteClicked = SingleLiveEvent<Void>()
    val onDeleteClicked : LiveData<Void> = _onDeleteClicked


    fun onAddEquipmentClicked() {
        _onAddEquipmentClicked.call()
    }

    fun onSignOutClicked() {
        _onSignOutClicked.call()
    }

    fun onDeleteClicked() {
        _onDeleteClicked.call()
    }

}