package com.example.skirental.viewmodels

import androidx.lifecycle.ViewModel
import com.example.skirental.enums.EquipmentType
import com.example.skirental.models.Equipment
import com.example.skirental.repositories.EquipmentRepository

class AdminDeleteViewModel(private val repository: EquipmentRepository) : ViewModel() {

    fun getAllEquipments(equipmentType: EquipmentType) = repository.getAllEquipments(equipmentType)

    fun deleteEquipment(equipment: Equipment) = repository.deleteEquipment(equipment)
}