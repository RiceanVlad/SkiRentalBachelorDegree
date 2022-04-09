package com.example.skirental.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.skirental.utils.SingleLiveEvent

class SearchViewModel : ViewModel() {
    private val _onNavigateToEquipmentFragment = SingleLiveEvent<Void>()
    val onNavigateToEquipmentFragment : LiveData<Void> = _onNavigateToEquipmentFragment

    fun onNavigateToEquipmentFragment() {
        _onNavigateToEquipmentFragment.call()
    }
}