package com.example.skirental.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {

    private val _stateFlow = MutableStateFlow(0)
        val stateFlow = _stateFlow.asStateFlow()
    
    private val _sharedFlow = MutableSharedFlow<Unit>(replay = 1)
    val sharedFlow = _sharedFlow.asSharedFlow()

    init {
//        viewModelScope.launch {
//            sharedFlow.collect {
//                delay(2000L)
//                println("FIRST FLOW: The received nubmer is $it")
//            }
//        }
//        viewModelScope.launch {
//            sharedFlow.collect {
//                delay(3000L)
//                println("SECOND FLOW: The received nubmer is $it")
//            }
//        }
        onShowToast()
    }

    private fun onShowToast() {
        viewModelScope.launch {
            _sharedFlow.emit(Unit)
        }
    }

    fun incrementCounter() {
        _stateFlow.value += 1
    }
}
