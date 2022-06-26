package com.example.skirental.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.skirental.utils.SingleLiveEvent

class AccountViewModel : ViewModel() {

    private val _onSignOutClicked = SingleLiveEvent<Void>()
    val onSignOutClicked : LiveData<Void> = _onSignOutClicked

    fun onSignOutClicked() {
        _onSignOutClicked.call()
    }
}