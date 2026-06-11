package com.example.tam.ui.screen.profile

import androidx.compose.runtime.Composable

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onLogoutSuccess: () -> Unit
) {
    ProfileContent(
        uiState = viewModel.uiState.value,
        onLogoutClick = {
            viewModel.logout(onLogoutSuccess)
        }
    )
}
