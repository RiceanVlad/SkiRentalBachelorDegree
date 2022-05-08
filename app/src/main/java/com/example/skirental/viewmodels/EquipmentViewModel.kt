package com.example.skirental.viewmodels

import androidx.lifecycle.ViewModel
import com.example.skirental.models.Equipment
import com.example.skirental.repositories.EquipmentRepository

class EquipmentViewModel(private val repository: EquipmentRepository) : ViewModel() {

    fun getAllEquipments() = repository.getAllEquipments()

    fun addEquipment(equipment: Equipment) = repository.addEquipment(equipment)


//    private val db: FirebaseFirestore = Firebase.firestore
//    private val mAuth = FirebaseAuth.getInstance()
//
//    private val _itemsList = MutableLiveData<MutableList<Equipment>>()
//    val itemsList : LiveData<MutableList<Equipment>> = _itemsList
//
//    init {
//        viewModelScope.launch {
//            val firestoreData = getItemsFromFirestoreCoroutines("VGm6D7t3LCxMxmR8Cbx4")
//            println("vlad: ${firestoreData.toString()}")
//            val items = mutableListOf<Equipment>()
//            firestoreData?.forEach { document ->
//                items.add(Equipment(
//                    document.id,
//                    document.get("description") as String,
//                    document.get("length").toString().toInt() ,
//                    document.get("usage").toString().toInt(),
//                ))
//            }
//            _itemsList.value = items
//        }
//    }
//
//    private suspend fun getItemsFromFirestoreCoroutines(childName: String)
//    : List<DocumentSnapshot>?{
//        return try {
//            val data = db
//                .collection("Items")
//                .document(childName)
//                .collection("Skis")
//                .get()
//                .await()
//            data.documents
//        } catch (e: Exception) {
//            null
//        }
//    }

}