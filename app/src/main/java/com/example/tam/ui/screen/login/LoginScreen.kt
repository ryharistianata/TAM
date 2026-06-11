package com.example.tam.ui.screen.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginSuccess: () -> Unit
) {
    val uiState = viewModel.uiState.value

    LaunchedEffect(uiState) {
        if (uiState is LoginUIState.Success) {
            onLoginSuccess()
            viewModel.resetState()
        }
    }

    LoginContent(
        uiState = uiState,
        onLoginClick = { email, password ->
            viewModel.login(email, password)
        },
        onResetError = {
            viewModel.resetState()
        }
    )
}
