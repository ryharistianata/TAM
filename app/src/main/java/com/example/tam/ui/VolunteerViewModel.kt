package com.example.tam.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tam.model.VolunteerEvent
import com.example.tam.repository.VolunteerRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

sealed interface VolunteerUiState {
    data class Success(val events: List<VolunteerEvent>) : VolunteerUiState
    object Error : VolunteerUiState
    object Loading : VolunteerUiState
}

class VolunteerViewModel(private val repository: VolunteerRepository) : ViewModel() {
    var uiState: VolunteerUiState by mutableStateOf(VolunteerUiState.Loading)
        private set

    var loadingJoinId: Int? by mutableStateOf(null)
        private set

    init {
        getEvents()
    }

    fun getEvents() {
        viewModelScope.launch {
            uiState = VolunteerUiState.Loading
            uiState = try {
                VolunteerUiState.Success(repository.getEvents())
            } catch (e: Exception) {
                VolunteerUiState.Error
            }
        }
    }

    fun toggleFavorite(id: Int) {
        val currentState = uiState
        if (currentState is VolunteerUiState.Success) {
            val updatedEvents = currentState.events.map {
                if (it.id == id) it.copy(isFavorite = !it.isFavorite) else it
            }
            uiState = VolunteerUiState.Success(updatedEvents)
        }
    }

    fun joinEvent(id: Int, onComplete: (String) -> Unit) {
        val currentState = uiState
        if (currentState is VolunteerUiState.Success) {
            val event = currentState.events.find { it.id == id }
            if (event != null && !event.isJoined) {
                viewModelScope.launch {
                    loadingJoinId = id
                    delay(2000) // Simulate network delay
                    val updatedEvents = currentState.events.map {
                        if (it.id == id) it.copy(isJoined = true) else it
                    }
                    uiState = VolunteerUiState.Success(updatedEvents)
                    loadingJoinId = null
                    onComplete("Berhasil bergabung di aksi: ${event.nama ?: ""}")
                }
            }
        }
    }

    companion object {
        fun Factory(repository: VolunteerRepository): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return VolunteerViewModel(repository) as T
            }
        }
    }
}
