package com.example.skirental.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.skirental.utils.SingleLiveEvent

class AccountViewModel : ViewModel() {

    private val _onSignOutClicked = SingleLiveEvent<Void>()
    val onSignOutClicked : LiveData<Void> = _onSignOutClicked

    private val _onPersonalDetailsClicked = SingleLiveEvent<Void>()
    val onPersonalDetailsClicked : LiveData<Void> = _onPersonalDetailsClicked

    private val _onAccountDetailsClicked = SingleLiveEvent<Void>()
    val onAccountDetailsClicked : LiveData<Void> = _onAccountDetailsClicked

    private val _onTermsAndCondClicked = SingleLiveEvent<Void>()
    val onTermsAndCondClicked : LiveData<Void> = _onTermsAndCondClicked

    fun onSignOutClicked() {
        _onSignOutClicked.call()
    }

    fun onPersonalDetailsClicked() {
        _onPersonalDetailsClicked.call()
    }

    fun onAccountDetailsClicked() {
        _onAccountDetailsClicked.call()
    }

    fun onTermsAndCondClicked() {
        _onTermsAndCondClicked.call()
    }
}