package com.example.skirental.ui.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.lifecycleScope
import com.example.skirental.R
import com.example.skirental.utils.Constants
import com.example.skirental.utils.State
import com.google.firebase.firestore.FirebaseFirestore
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.coroutines.launch
import timber.log.Timber

class AdminActivity : AppCompatActivity() {

    private val mEquipmentCollection = FirebaseFirestore.getInstance()
        .collection(Constants.FIRESTORE_ITEMS_COLLECTION)
        .document(Constants.FIRESTORE_ITEMS_DOCUMENT_ID)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK) {
            val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if (result != null)
                if (result.contents == null) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
                } else {
                    val collections = ArrayList<String>()
                    collections.addAll(arrayListOf(
                        Constants.FIRESTORE_SKI_COLLECTION,
                        Constants.FIRESTORE_SKI_BOOTS_COLLECTION,
                    ))
                    for(collection in collections){
                        lifecycleScope.launch {
                            updateRentState(collection, result.contents)
                        }
                    }

                    Toast.makeText(this, "Product: ${result.contents} is now updated and rent ready.", Toast.LENGTH_LONG).show()
                }
            else {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

    private suspend fun updateRentState(collection: String, documentId: String) {
        mEquipmentCollection.collection(collection).document(documentId)
            .update("rented",false)
            .addOnSuccessListener { Timber.d("DocumentSnapshot successfully updated!") }
            .addOnFailureListener { e -> Timber.w(e, "Error updating document") }
    }
}