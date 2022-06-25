package com.example.skirental.viewmodels

import androidx.lifecycle.ViewModel
import com.example.skirental.repositories.UserRepository

class CalendarViewModel(private val repository: UserRepository) : ViewModel() {

    fun addRentDates(dateStart: String, dateEnd: String) = repository.addRentDates(dateStart, dateEnd)
}