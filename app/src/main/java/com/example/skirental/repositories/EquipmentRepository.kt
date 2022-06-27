package com.example.skirental.repositories

import android.net.Uri
import android.os.Build.VERSION_CODES.P
import com.example.skirental.enums.EquipmentType
import com.example.skirental.models.Equipment
import com.example.skirental.utils.Constants
import com.example.skirental.utils.State
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class EquipmentRepository {

    private val mAuth =  FirebaseAuth.getInstance()

    private val mEquipmentCollection = FirebaseFirestore.getInstance()
        .collection(Constants.FIRESTORE_ITEMS_COLLECTION)
        .document(Constants.FIRESTORE_ITEMS_DOCUMENT_ID)

    private val mCartItemsCollection = FirebaseFirestore.getInstance()
        .collection(Constants.FIRESTORE_USERS_COLLECTION)
        .document(mAuth.uid.toString())
        .collection(Constants.FIRESTORE_CART_ITEMS_COLLECTION)

    fun getAllEquipments(equipmentType: EquipmentType) = flow<State<List<Equipment>>>{
        // Emit loading state
        emit(State.loading())

        val snapshot = mEquipmentCollection.collection(equipmentType.string).get().await()
        val equipments = snapshot.toObjects(Equipment::class.java)

        // Emit success state with data
        emit(State.success(equipments))
    }.catch {
        // If exception is thrown, emit failed state along with message.
        emit(State.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)


    fun addEquipment(equipment: Equipment, equipmentId: String = "") = flow<State<Equipment>> {
        emit(State.loading())

        if(equipmentId.isEmpty()) {
            mEquipmentCollection.collection(equipment.type).add(equipment).await()
        } else {
            mEquipmentCollection.collection(equipment.type).document(equipmentId).set(equipment).await()
        }

        emit(State.success(equipment))
    }.catch {
        emit(State.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)


    fun deleteEquipment(equipment: Equipment) = flow<State<Equipment>> {
        emit(State.loading())

        mEquipmentCollection.collection(equipment.type).document(equipment.id).delete().await()
        val extension = ".jpg"
        val refStorage = FirebaseStorage.getInstance().reference.child("${equipment.type}/${equipment.id}$extension")
        refStorage.delete().await()

        emit(State.success(equipment))
    }.catch {
        emit(State.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)


    fun getAllCartEquipments() = flow<State<List<Equipment>>>{
        emit(State.loading())
        val snapshot = mCartItemsCollection.get().await()
        val equipments = snapshot.toObjects(Equipment::class.java)

        emit(State.success(equipments))
    }.catch {
        emit(State.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)


    fun addEquipmentToCart(equipment: Equipment) = flow<State<DocumentReference>> {
        emit(State.loading())
        mCartItemsCollection.document(equipment.id).set(equipment)

        emit(State.success(mCartItemsCollection.document(equipment.id)))
    }.catch {
        emit(State.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)


    fun updateRentStateForItems(equipmentList: Array<Equipment>, rentState: Boolean = false) = flow<State<Equipment>> {
        emit(State.loading())

        equipmentList.forEach { equipment ->
            mEquipmentCollection.collection(equipment.type).document(equipment.id).update(Constants.FIRESTORE_USER_IS_RENTED, rentState).await()
        }

        emit(State.success(Equipment()))
    }.catch {
        emit(State.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)


    fun addEquipmentImageToStorage(equipment: Equipment, fileUri: Uri) = flow<State<StorageReference>> {
        emit(State.loading())

        val extension = ".jpg"
        val refStorage = FirebaseStorage.getInstance().reference.child("${equipment.type}/${equipment.id}$extension")
        refStorage.putFile(fileUri).await()

        emit(State.success(refStorage))
    }.catch {
        emit(State.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)
}