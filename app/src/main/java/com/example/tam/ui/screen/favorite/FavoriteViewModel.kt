package com.example.tam.ui.screen.favorite

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.tam.data.model.response.VolunteerResponse

class FavoriteViewModel : ViewModel() {
    private val _favorites = mutableStateListOf<VolunteerResponse>()
    val favorites: List<VolunteerResponse> get() = _favorites

    private val _uiState = mutableStateOf<FavoriteUIState>(FavoriteUIState.Empty)
    val uiState: State<FavoriteUIState> = _uiState

    fun addFavorite(activity: VolunteerResponse) {
        if (!_favorites.any { it.id == activity.id }) {
            _favorites.add(activity)
            updateUiState()
        }
    }

    fun removeFavorite(activityId: String) {
        _favorites.removeAll { it.id == activityId }
        updateUiState()
    }

    private fun updateUiState() {
        _uiState.value = if (_favorites.isEmpty()) {
            FavoriteUIState.Empty
        } else {
            FavoriteUIState.Success(_favorites.toList())
        }
    }
}
