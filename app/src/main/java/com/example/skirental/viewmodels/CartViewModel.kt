package com.example.skirental.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.*

class CartViewModel : ViewModel() {

    private val _stateFlow = MutableStateFlow(0)
        val stateFlow = _stateFlow.asStateFlow()

    fun incrementCounter() {
        _stateFlow.value += 1
    }
}
