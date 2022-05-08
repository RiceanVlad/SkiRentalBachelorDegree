package com.example.skirental.repositories

import com.example.skirental.models.Equipment
import com.example.skirental.utils.State
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class EquipmentRepository {
    private val mEquipmentsCollection = FirebaseFirestore.getInstance()
        .collection("Items")
        .document("VGm6D7t3LCxMxmR8Cbx4")
        .collection("Skis")

    fun getAllEquipments() = flow<State<List<Equipment>>>{

        // Emit loading state
        emit(State.loading())

        val snapshot = mEquipmentsCollection.get().await()
        val equipments = snapshot.toObjects(Equipment::class.java)

        // Emit success state with data
        emit(State.success(equipments))
    }.catch {
        // If exception is thrown, emit failed state along with message.
        emit(State.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)


    fun addEquipment(equipment: Equipment) = flow<State<DocumentReference>> {

        // Emit loading state
        emit(State.loading())

        val equipmentRef = mEquipmentsCollection.add(equipment).await()

        // Emit success state with equipment reference
        emit(State.success(equipmentRef))
    }.catch {
        // If exception is thrown, emit failed state along with message.
        emit(State.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)
}