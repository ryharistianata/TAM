package com.example.tam.ui.screen.detail

import com.example.tam.data.model.response.VolunteerResponse

sealed class DetailUIState {
    object Loading : DetailUIState()
    data class Success(val data: VolunteerResponse) : DetailUIState()
    data class Error(val message: String) : DetailUIState()
}
