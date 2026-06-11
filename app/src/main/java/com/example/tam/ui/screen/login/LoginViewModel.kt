package com.example.tam.ui.screen.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    private val _uiState = mutableStateOf<LoginUIState>(LoginUIState.Idle)
    val uiState: State<LoginUIState> = _uiState

    fun login(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _uiState.value = LoginUIState.Error("Email dan password tidak boleh kosong")
            return
        }

        _uiState.value = LoginUIState.Loading
        
        // Simulasi login sukses (dummy)
        _uiState.value = LoginUIState.Success
    }

    fun resetState() {
        _uiState.value = LoginUIState.Idle
    }
}
