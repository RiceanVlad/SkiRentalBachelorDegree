package com.example.skirental.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skirental.models.Equipment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import java.lang.Exception

@RequiresApi(Build.VERSION_CODES.N)
class EquipmentViewModel : ViewModel() {

    private val db: FirebaseFirestore = Firebase.firestore
    private val mAuth = FirebaseAuth.getInstance()

    private val _itemsList = MutableLiveData<List<Equipment>>()
    val itemsList : LiveData<List<Equipment>> = _itemsList

//    val itemsListFlow: Flow<List<Equipment>> = flow {
//
//    }

    init {
        viewModelScope.launch {
            val firestoreData = getItemsFromFirestoreCoroutines("nRjphMC2HAAG1AAIlXJP")
            Timber.i("vlad: ${firestoreData.toString()}")
            println("vlad: ${firestoreData.toString()}")
        }
    }

    private suspend fun getItemsFromFirestoreCoroutines(childName: String): DocumentSnapshot?{
        return try {
            val data = db
                .collection("Items")
                .document("VGm6D7t3LCxMxmR8Cbx4")
                .collection("Skis")
                .document(childName)
                .get()
                .await()
            data
        } catch (e: Exception) {
            null
        }
    }

}