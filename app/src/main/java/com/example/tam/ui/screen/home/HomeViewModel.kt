package com.example.tam.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tam.data.repository.VolunteerRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: VolunteerRepository) : ViewModel() {

    private val _uiState = mutableStateOf<HomeUIState>(HomeUIState.Loading)
    val uiState: State<HomeUIState> = _uiState

    init {
        fetchActivities()
    }

    fun fetchActivities() {
        viewModelScope.launch {
            _uiState.value = HomeUIState.Loading
            try {
                val response = repository.getActivities()
                _uiState.value = HomeUIState.Success(response)
            } catch (e: Exception) {
                _uiState.value = HomeUIState.Error(e.message ?: "Gagal memuat data")
            }
        }
    }
}
