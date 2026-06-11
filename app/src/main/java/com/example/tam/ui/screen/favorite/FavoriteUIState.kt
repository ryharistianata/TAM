package com.example.tam.ui.screen.favorite

import com.example.tam.data.model.response.VolunteerResponse

sealed class FavoriteUIState {
    object Empty : FavoriteUIState()
    data class Success(val data: List<VolunteerResponse>) : FavoriteUIState()
}
