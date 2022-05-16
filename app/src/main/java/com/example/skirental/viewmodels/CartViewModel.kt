package com.example.skirental.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skirental.enums.EquipmentType
import com.example.skirental.models.Equipment
import com.example.skirental.repositories.EquipmentRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CartViewModel(private val repository: EquipmentRepository) : ViewModel() {

    fun getAllCartEquipments() = repository.getAllCartEquipments()

}
