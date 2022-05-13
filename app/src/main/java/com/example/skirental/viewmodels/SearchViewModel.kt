package com.example.skirental.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.skirental.enums.EquipmentType
import com.example.skirental.utils.SingleLiveEvent

class SearchViewModel : ViewModel() {
    private val _onNavigateToEquipmentFragment = SingleLiveEvent<EquipmentType>()
    val onNavigateToEquipmentFragment : LiveData<EquipmentType> = _onNavigateToEquipmentFragment

    fun onNavigateToEquipmentFragment(equipmentType: EquipmentType) {
        _onNavigateToEquipmentFragment.value = equipmentType
    }
}