package com.example.skirental.repositories

import com.example.skirental.utils.Constants
import com.example.skirental.utils.State
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class UserRepository {

    private val mAuth =  FirebaseAuth.getInstance()

    private val mUserDocument = FirebaseFirestore.getInstance()
        .collection(Constants.FIRESTORE_USERS_COLLECTION)
        .document(mAuth.uid.toString())

    fun addRentDates(dateStart: String, dateEnd: String) = flow<State<DocumentReference>> {
        emit(State.loading())

        mUserDocument.update(Constants.FIRESTORE_START_DATE, dateStart).await()
        mUserDocument.update(Constants.FIRESTORE_END_DATE, dateEnd).await()

        emit(State.success(mUserDocument))
    }.catch {
        emit(State.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

}