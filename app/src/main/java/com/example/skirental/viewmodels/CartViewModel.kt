package com.example.skirental.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skirental.enums.EquipmentType
import com.example.skirental.models.Equipment
import com.example.skirental.repositories.EquipmentRepository
import com.example.skirental.utils.SingleLiveEvent
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CartViewModel(private val repository: EquipmentRepository) : ViewModel() {

    private val _onNavigateToSearchScreen = SingleLiveEvent<Void>()
    val onNavigateToSearchScreen : LiveData<Void> = _onNavigateToSearchScreen

    private val _onNavigateToCalendarScreen = SingleLiveEvent<Void>()
    val onNavigateToCalendarScreen : LiveData<Void> = _onNavigateToCalendarScreen

    fun getAllCartEquipments() = repository.getAllCartEquipments()

    fun onNavigateToSearchScreen() {
        _onNavigateToSearchScreen.call()
    }

    fun onNavigateToCalendarScreen() {
        _onNavigateToCalendarScreen.call()
    }

}
