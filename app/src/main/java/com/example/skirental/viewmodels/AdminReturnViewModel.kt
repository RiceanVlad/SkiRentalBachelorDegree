package com.example.skirental.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.skirental.utils.SingleLiveEvent

class AdminReturnViewModel : ViewModel() {

    private val _onScanEquipmentClicked = SingleLiveEvent<Void>()
    val onScanEquipmentClicked : LiveData<Void> = _onScanEquipmentClicked

    fun onScanEquipmentClicked() {
        _onScanEquipmentClicked.call()
    }
}