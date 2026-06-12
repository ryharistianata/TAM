package com.example.tam.ui.screen.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _uiState = mutableStateOf<LoginUIState>(LoginUIState.Idle)
    val uiState: State<LoginUIState> = _uiState

    fun login(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _uiState.value = LoginUIState.Error("Email dan password tidak boleh kosong")
            return
        }

        viewModelScope.launch {
            _uiState.value = LoginUIState.Loading
            
            // Simulasi delay jaringan agar terasa nyata
            delay(1500)

            if (email == "user@gmail.com" && password == "password123") {
                _uiState.value = LoginUIState.Success
            } else {
                _uiState.value = LoginUIState.Error("Email atau password salah")
            }
        }
    }

    fun resetState() {
        _uiState.value = LoginUIState.Idle
    }
}
