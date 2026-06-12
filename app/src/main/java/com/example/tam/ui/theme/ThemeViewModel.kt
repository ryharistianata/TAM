package com.example.tam.ui.theme

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ThemeViewModel : ViewModel() {
    private val _isDarkMode = mutableStateOf(false)
    val isDarkMode: State<Boolean> = _isDarkMode

    fun toggleDarkMode(dark: Boolean) {
        _isDarkMode.value = dark
    }
}
