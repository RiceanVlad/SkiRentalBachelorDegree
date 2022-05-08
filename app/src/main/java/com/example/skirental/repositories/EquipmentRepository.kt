package com.example.skirental.repositories

import com.example.skirental.enums.EquipmentType
import com.example.skirental.models.Equipment
import com.example.skirental.utils.Constants
import com.example.skirental.utils.State
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class EquipmentRepository {
    private val mEquipmentCollection = FirebaseFirestore.getInstance()
        .collection(Constants.FIRESTORE_ITEMS_COLLECTION)
        .document(Constants.FIRESTORE_ITEMS_DOCUMENT_ID)

    fun getAllEquipments(equipmentType: EquipmentType) = flow<State<List<Equipment>>>{

        // Emit loading state
        emit(State.loading())

        val snapshot = when(equipmentType) {
            EquipmentType.SKI -> {
                mEquipmentCollection.collection(Constants.FIRESTORE_SKI_COLLECTION).get().await()
            }
            EquipmentType.SKI_BOOTS -> {
                mEquipmentCollection.collection(Constants.FIRESTORE_SKI_BOOTS_COLLECTION).get().await()
            }
        }
        val equipments = snapshot.toObjects(Equipment::class.java)

        // Emit success state with data
        emit(State.success(equipments))
    }.catch {
        // If exception is thrown, emit failed state along with message.
        emit(State.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)


    fun addEquipment(equipment: Equipment, equipmentType: EquipmentType) = flow<State<DocumentReference>> {

        // Emit loading state
        emit(State.loading())

        val equipmentRef = when(equipmentType) {
            EquipmentType.SKI -> {
                mEquipmentCollection.collection(Constants.FIRESTORE_SKI_COLLECTION).add(equipment).await()
            }
            EquipmentType.SKI_BOOTS -> {
                mEquipmentCollection.collection(Constants.FIRESTORE_SKI_BOOTS_COLLECTION).add(equipment).await()
            }
        }

        // Emit success state with equipment reference
        emit(State.success(equipmentRef))
    }.catch {
        // If exception is thrown, emit failed state along with message.
        emit(State.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)
}