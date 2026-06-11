package com.example.tam.ui.screen.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {
    private val _uiState = mutableStateOf(ProfileUIState())
    val uiState: State<ProfileUIState> = _uiState

    fun logout(onLogoutSuccess: () -> Unit) {
        // Logic logout (misal clear SessionManager)
        onLogoutSuccess()
    }
}
