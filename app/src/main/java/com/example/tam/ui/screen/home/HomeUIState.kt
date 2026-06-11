package com.example.tam.ui.screen.home

import com.example.tam.data.model.response.VolunteerResponse

sealed class HomeUIState {
    object Loading : HomeUIState()
    data class Success(val data: List<VolunteerResponse>) : HomeUIState()
    data class Error(val message: String) : HomeUIState()
}
