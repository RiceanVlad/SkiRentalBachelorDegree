package com.example.skirental.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.skirental.R
import com.example.skirental.utils.Prefs

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val prefs = Prefs(applicationContext)

        // getter
        var value = prefs.userHasDetails

        // setter
        prefs.userHasDetails = true
    }
}