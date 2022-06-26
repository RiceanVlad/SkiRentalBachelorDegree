package com.example.skirental.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.skirental.utils.SingleLiveEvent

class AccountViewModel : ViewModel() {

    private val _onSignOutClicked = SingleLiveEvent<Void>()
    val onSignOutClicked : LiveData<Void> = _onSignOutClicked

    private val _onPersonalDetailsClicked = SingleLiveEvent<Void>()
    val onPersonalDetailsClicked : LiveData<Void> = _onPersonalDetailsClicked

    fun onSignOutClicked() {
        _onSignOutClicked.call()
    }

    fun onPersonalDetailsClicked() {
        _onPersonalDetailsClicked.call()
    }
}