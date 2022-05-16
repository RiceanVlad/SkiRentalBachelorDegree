package com.example.skirental.viewmodelfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.skirental.repositories.EquipmentRepository
import com.example.skirental.viewmodels.CartViewModel
import com.example.skirental.viewmodels.DetailsEquipmentViewModel

class CartViewModelFactory : ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(repository = EquipmentRepository()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}