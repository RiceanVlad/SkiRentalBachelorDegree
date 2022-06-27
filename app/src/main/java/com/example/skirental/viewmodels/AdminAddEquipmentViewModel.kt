package com.example.skirental.viewmodels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.skirental.models.Equipment
import com.example.skirental.repositories.EquipmentRepository
import com.example.skirental.utils.SingleLiveEvent
import com.google.android.gms.tasks.Tasks.call

class AdminAddEquipmentViewModel(private val repository: EquipmentRepository) : ViewModel() {

    private val _onAddImageClicked = SingleLiveEvent<Void>()
    val onAddImageClicked : LiveData<Void> = _onAddImageClicked

    private val _onAddEquipmentClicked = SingleLiveEvent<Void>()
    val onAddEquipmentClicked : LiveData<Void> = _onAddEquipmentClicked

    fun onAddImageClicked() {
        _onAddImageClicked.call()
    }

    fun onAddEquipmentClicked() {
        _onAddEquipmentClicked.call()
    }

    fun addEquipmentImageToStorage(equipment: Equipment, fileUri: Uri) = repository.addEquipmentImageToStorage(equipment, fileUri)

    fun addEquipment(equipment: Equipment, equipmentId: String) = repository.addEquipment(equipment, equipmentId)

}