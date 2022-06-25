package com.example.skirental.viewmodelfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.skirental.repositories.UserRepository
import com.example.skirental.viewmodels.LoginDetailsViewModel

class LoginDetailsViewModelFactory : ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LoginDetailsViewModel::class.java)) {
            return LoginDetailsViewModel(repository = UserRepository()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
