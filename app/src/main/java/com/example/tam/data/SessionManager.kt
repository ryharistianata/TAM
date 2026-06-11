package com.example.tam.data

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun saveAuthToken(token: String) {
        prefs.edit().putString("auth_token", token).apply()
    }

    fun fetchAuthToken(): String? = prefs.getString("auth_token", null)

    fun clear() {
        prefs.edit().clear().apply()
    }
}
