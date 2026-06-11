package com.example.tam.ui.screen.favorite

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class FavoriteViewModel : ViewModel() {
    private val _uiState = mutableStateOf<FavoriteUIState>(FavoriteUIState.Empty)
    val uiState: State<FavoriteUIState> = _uiState

    // In a real app, this would be fetched from a local database (Room)
    fun fetchFavorites() {
        _uiState.value = FavoriteUIState.Empty
    }
}
