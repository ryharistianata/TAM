package com.example.tam.ui.screen.profile

import androidx.compose.runtime.Composable

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onLogoutSuccess: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    ProfileContent(
        uiState = viewModel.uiState.value,
        onLogoutClick = {
            viewModel.logout(onLogoutSuccess)
        },
        onSettingsClick = onNavigateToSettings,
        onUpdateProfile = { name, email, phone ->
            viewModel.updateProfile(name, email, phone)
        }
    )
}
