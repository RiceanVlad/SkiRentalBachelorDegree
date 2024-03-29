package com.example.skirental.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.skirental.utils.SingleLiveEvent

class SignInViewModel: ViewModel() {
    private val _onSignInClicked = SingleLiveEvent<Void>()
    val onSignInClicked : LiveData<Void> = _onSignInClicked

    fun onSignInClicked() {
        _onSignInClicked.call()
    }
}