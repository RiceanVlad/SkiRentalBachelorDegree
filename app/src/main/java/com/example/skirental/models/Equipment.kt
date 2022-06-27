package com.example.skirental.models

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Equipment (
    @DocumentId
    @SerialName("id") val id: String = "",
    @SerialName("type") var type: String = "",
    @SerialName("description") var description: String = "",
    @SerialName("usage") var usage: Int = 0,
    @SerialName("length") var length: Int = 0,
    @SerialName("shoeSize") var shoeSize: Int = 0,
    @SerialName("price") var price: Int = 0,
    @SerialName("long_description") var long_description: String = "",
    @SerialName("rented") val rented: Boolean = false,
    ): java.io.Serializable, Parcelable
