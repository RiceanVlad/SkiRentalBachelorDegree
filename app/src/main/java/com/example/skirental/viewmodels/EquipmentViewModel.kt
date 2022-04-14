package com.example.skirental.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.skirental.models.Equipment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import timber.log.Timber

@RequiresApi(Build.VERSION_CODES.N)
class EquipmentViewModel : ViewModel() {

    private val db: FirebaseFirestore = Firebase.firestore
    private val mAuth = FirebaseAuth.getInstance()

    private val _itemsList = MutableLiveData<List<Equipment>>()
    val itemsList : LiveData<List<Equipment>> = _itemsList

    init {
        getItemsFromFirestore()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getItemsFromFirestore() {
        db.collection("Items").document("VGm6D7t3LCxMxmR8Cbx4").collection("Skis")
            .get()
            .addOnSuccessListener { result ->
                val items = mutableListOf<Equipment>()
                for (document in result) {
                    Timber.i("${document.id} => ${document.data}")
                    items.add(
                        Equipment(
                            document.id,
                            document.data.getValue("description").toString(),
                            document.data.getValue("usage").toString().toInt(),
                            document.data.getValue("length").toString().toInt(),
                            document.data.getOrDefault("shoeSize", "a").toString().toInt(),
                        )
                    )
                }
                _itemsList.value = items
            }
            .addOnFailureListener { exception ->
                Timber.w("Error getting documents.", exception)
            }
    }
}