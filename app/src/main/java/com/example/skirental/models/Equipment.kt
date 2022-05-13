package com.example.skirental.models

import com.google.firebase.firestore.DocumentId

data class Equipment (
    @DocumentId
    val id: String = "",
    val type: String = "",
    val description: String = "",
    val usage: Int = 0,
    val length: Int = 0,
    val shoeSize: Int = 0,
)
