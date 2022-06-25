package com.example.skirental.viewmodels

import androidx.lifecycle.ViewModel
import com.example.skirental.repositories.UserRepository

class PayViewModel(private val repository: UserRepository) : ViewModel() {

    fun addAdditionalComment(comment: String) = repository.addAdditionalComment(comment)

}