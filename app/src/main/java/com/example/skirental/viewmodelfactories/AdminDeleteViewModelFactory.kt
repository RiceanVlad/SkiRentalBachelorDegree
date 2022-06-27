package com.example.skirental.viewmodelfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.skirental.repositories.EquipmentRepository
import com.example.skirental.viewmodels.AdminDeleteViewModel

class AdminDeleteViewModelFactory : ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AdminDeleteViewModel::class.java)) {
            return AdminDeleteViewModel(repository = EquipmentRepository()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

