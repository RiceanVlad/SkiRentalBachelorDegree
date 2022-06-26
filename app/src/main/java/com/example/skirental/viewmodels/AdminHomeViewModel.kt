package com.example.skirental.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.skirental.utils.SingleLiveEvent

class AdminHomeViewModel : ViewModel() {

    private val _onAddEquipmentClicked = SingleLiveEvent<Void>()
    val onAddEquipmentClicked : LiveData<Void> = _onAddEquipmentClicked

    fun onAddEquipmentClicked() {
        _onAddEquipmentClicked.call()
    }}