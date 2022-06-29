package com.example.skirental.viewmodels

import androidx.lifecycle.ViewModel
import com.example.skirental.models.Equipment
import com.example.skirental.repositories.EquipmentRepository
import com.example.skirental.repositories.UserRepository

class PayViewModel(private val userRepository: UserRepository, private val equipmentRepository: EquipmentRepository) : ViewModel() {

    fun addAdditionalComment(comment: String) = userRepository.addAdditionalComment(comment)

    fun updateRentStateForItemsFirestore(equipmentList: Array<Equipment>, rentState: Boolean) = equipmentRepository.updateRentStateForItems(equipmentList, rentState)

    fun removeAllEquipmentsFromCart(equipmentList: Array<Equipment>) = equipmentRepository.removeAllEquipmentsFromCart(equipmentList)

}