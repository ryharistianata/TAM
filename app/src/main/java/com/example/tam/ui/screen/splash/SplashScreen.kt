package com.example.tam.ui.screen.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToLogin: () -> Unit
) {
    LaunchedEffect(key1 = true) {
        delay(2000L)
        onNavigateToLogin()
    }

    SplashContent()
}
