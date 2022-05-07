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
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Exception

@RequiresApi(Build.VERSION_CODES.N)
class EquipmentViewModel : ViewModel() {

    private val db: FirebaseFirestore = Firebase.firestore
    private val mAuth = FirebaseAuth.getInstance()

    private val _itemsList = MutableLiveData<MutableList<Equipment>>()
    val itemsList : LiveData<MutableList<Equipment>> = _itemsList



    init {
        viewModelScope.launch {
            val firestoreData = getItemsFromFirestoreCoroutines("VGm6D7t3LCxMxmR8Cbx4")
            println("vlad: ${firestoreData.toString()}")
            val items = mutableListOf<Equipment>()
            firestoreData?.forEach { document ->
                items.add(Equipment(
                    document.id,
                    document.get("description") as String,
                    document.get("length").toString().toInt() ,
                    document.get("usage").toString().toInt(),
                ))
            }
            _itemsList.value = items
        }
    }

    private suspend fun getItemsFromFirestoreCoroutines(childName: String)
    : List<DocumentSnapshot>?{
        return try {
            val data = db
                .collection("Items")
                .document(childName)
                .collection("Skis")
                .get()
                .await()
            data.documents
        } catch (e: Exception) {
            null
        }
    }

}