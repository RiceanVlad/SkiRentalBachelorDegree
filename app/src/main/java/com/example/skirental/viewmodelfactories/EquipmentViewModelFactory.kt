package com.example.skirental.viewmodelfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.skirental.repositories.EquipmentRepository
import com.example.skirental.viewmodels.EquipmentViewModel

class EquipmentViewModelFactory : ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(EquipmentViewModel::class.java)) {
            return EquipmentViewModel(repository = EquipmentRepository()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}