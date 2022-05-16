package com.example.skirental.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class DetailsEquipmentViewModel : ViewModel() {

    private val _onAddToCartClicked = MutableSharedFlow<Unit>(replay = 0)
    val onAddToCartClicked = _onAddToCartClicked.asSharedFlow()

    fun onAddToCartClicked() {
        viewModelScope.launch {
            _onAddToCartClicked.emit(Unit)
        }
    }
}