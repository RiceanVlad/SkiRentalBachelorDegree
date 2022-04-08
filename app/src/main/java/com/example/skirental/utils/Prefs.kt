package com.example.skirental.utils

import android.content.Context
import com.example.skirental.R
import com.example.skirental.utils.Constants.USER_DETAILS
import com.example.skirental.utils.Constants.USER_HAS_DETAILS

class Prefs(context: Context) {

    private val preferences = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE)

    var userHasDetails: Boolean
        get() = preferences.getBoolean(USER_HAS_DETAILS, false)
        set(value) = preferences.edit().putBoolean(USER_HAS_DETAILS, value).apply()

    var userDetails: String?
        get() = preferences.getString(USER_DETAILS, "")
        set(value) = preferences.edit().putString(USER_DETAILS, value).apply()
}