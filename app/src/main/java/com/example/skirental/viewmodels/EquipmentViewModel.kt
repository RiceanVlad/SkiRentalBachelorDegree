package com.example.skirental.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skirental.enums.EquipmentType
import com.example.skirental.models.Equipment
import com.example.skirental.repositories.EquipmentRepository
import com.example.skirental.utils.SingleLiveEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class EquipmentViewModel(private val repository: EquipmentRepository) : ViewModel() {

    fun getAllEquipments(equipmentType: EquipmentType) = repository.getAllEquipments(equipmentType)

    private val _onShowPopupEvent = SingleLiveEvent<Void>()
    val onShowPopupEvent : LiveData<Void> = _onShowPopupEvent

    fun onShowPopupEvent() {
        _onShowPopupEvent.call()
    }

}