package com.example.skirental.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skirental.enums.EquipmentType
import com.example.skirental.models.Equipment
import com.example.skirental.repositories.EquipmentRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class EquipmentViewModel(private val repository: EquipmentRepository) : ViewModel() {

    fun getAllEquipments() = repository.getAllEquipments(EquipmentType.SKI)

    fun addEquipment(equipment: Equipment) = repository.addEquipment(equipment, EquipmentType.SKI)

    private val _addEquipmentClickedSharedFlow = MutableSharedFlow<Unit>(replay = 0)
    val addEquipmentClickedSharedFlow = _addEquipmentClickedSharedFlow.asSharedFlow()

    fun addEquipmentClicked() {
        viewModelScope.launch {
            _addEquipmentClickedSharedFlow.emit(Unit)
        }
    }

}