package com.example.skirental.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.skirental.utils.SingleLiveEvent

class LoginDetailsViewModel : ViewModel() {
    private val _onNextClicked = SingleLiveEvent<Void>()
    val onNextClicked : LiveData<Void> = _onNextClicked

    fun onNextClicked() {
        _onNextClicked.call()
    }
}