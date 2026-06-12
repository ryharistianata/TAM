package com.example.tam.ui.screen.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {
    private val _uiState = mutableStateOf(ProfileUIState())
    val uiState: State<ProfileUIState> = _uiState

    fun updateProfile(name: String, email: String, phone: String) {
        _uiState.value = _uiState.value.copy(
            name = name,
            email = email,
            phone = phone
        )
    }

    fun logout(onLogoutSuccess: () -> Unit) {
        onLogoutSuccess()
    }
}
