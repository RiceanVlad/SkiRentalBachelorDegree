package com.example.skirental.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skirental.models.Equipment
import com.example.skirental.repositories.EquipmentRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class DetailsEquipmentViewModel(private val repository: EquipmentRepository) : ViewModel() {

    private val _onAddToCartClicked = MutableSharedFlow<Unit>(replay = 0)
    val onAddToCartClicked = _onAddToCartClicked.asSharedFlow()

    fun addEquipmentToCart(equipment: Equipment) = repository.addEquipmentToCart(equipment)

    fun onAddToCartClicked() {
        viewModelScope.launch {
            _onAddToCartClicked.emit(Unit)
        }
    }
}