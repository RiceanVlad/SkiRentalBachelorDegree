package com.example.skirental.models

import com.google.firebase.firestore.DocumentId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Equipment (
    @DocumentId
    @SerialName("id") val id: String = "",
    @SerialName("type") val type: String = "",
    @SerialName("description") val description: String = "",
    @SerialName("usage") val usage: Int = 0,
    @SerialName("length") var length: Int = 0,
    @SerialName("shoeSize") var shoeSize: Int = 0,
    @SerialName("price") val price: Int = 0,
    @SerialName("long_description") val long_description: String = "",
    ): java.io.Serializable
