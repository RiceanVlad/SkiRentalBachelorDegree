package com.example.skirental.viewmodels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.skirental.models.Equipment
import com.example.skirental.repositories.EquipmentRepository
import com.example.skirental.utils.SingleLiveEvent

class AdminAddEquipmentViewModel(private val repository: EquipmentRepository) : ViewModel() {

    private val _onAddImageClicked = SingleLiveEvent<Void>()
    val onAddImageClicked : LiveData<Void> = _onAddImageClicked

    fun onAddImageClicked() {
        _onAddImageClicked.call()
    }

    fun addEquipmentImageToStorage(equipment: Equipment, fileUri: Uri) = repository.addEquipmentImageToStorage(equipment, fileUri)

}