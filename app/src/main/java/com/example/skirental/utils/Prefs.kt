package com.example.skirental.utils

import android.content.Context
import com.example.skirental.R

class Prefs(context: Context) {

    private val preferences = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE)
    private val USER_HAS_DETAILS = "userHasDetails"

    var userHasDetails: Boolean
        get() = preferences.getBoolean(USER_HAS_DETAILS, false)
        set(value) = preferences.edit().putBoolean(USER_HAS_DETAILS, value).apply()
}