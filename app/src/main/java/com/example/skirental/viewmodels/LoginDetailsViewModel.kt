package com.example.skirental.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.skirental.repositories.EquipmentRepository
import com.example.skirental.repositories.UserRepository
import com.example.skirental.utils.SingleLiveEvent

class LoginDetailsViewModel(private val repository: UserRepository) : ViewModel() {

    private val _onNextClicked = SingleLiveEvent<Void>()
    val onNextClicked : LiveData<Void> = _onNextClicked

    fun onNextClicked() {
        _onNextClicked.call()
    }

    fun addUserPersonalDetailsToFirestore(height: Int, weight: Int, shoeSize: Int) = repository.addUserPersonalDetails(height, weight, shoeSize)
}