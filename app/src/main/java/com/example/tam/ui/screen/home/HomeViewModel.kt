package com.example.tam.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tam.data.model.response.VolunteerResponse
import com.example.tam.data.repository.VolunteerRepository
import kotlinx.coroutines.launch
import java.io.IOException

class HomeViewModel(private val repository: VolunteerRepository) : ViewModel() {

    private val _uiState = mutableStateOf<HomeUIState>(HomeUIState.Loading)
    val uiState: State<HomeUIState> = _uiState

    private var allActivities: List<VolunteerResponse> = emptyList()
    
    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    private val _selectedCategory = mutableStateOf("All")
    val selectedCategory: State<String> = _selectedCategory

    init {
        fetchActivities()
    }

    fun fetchActivities() {
        viewModelScope.launch {
            _uiState.value = HomeUIState.Loading
            try {
                val response = repository.getActivities()
                allActivities = response
                filterActivities()
            } catch (e: IOException) {
                _uiState.value = HomeUIState.Error("Tidak ada koneksi internet. Silakan periksa jaringan Anda.")
            } catch (e: Exception) {
                _uiState.value = HomeUIState.Error(e.message ?: "Terjadi kesalahan sistem")
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        filterActivities()
    }

    fun onCategorySelected(category: String) {
        _selectedCategory.value = category
        filterActivities()
    }

    private fun filterActivities() {
        val query = _searchQuery.value.lowercase()
        val category = _selectedCategory.value.lowercase()

        val filtered = allActivities.filter {
            val matchesSearch = it.title.lowercase().contains(query) || 
                               it.description.lowercase().contains(query)
            
            val matchesCategory = if (category == "all") true 
                                 else it.title.lowercase().contains(category) || 
                                      it.description.lowercase().contains(category)
            
            matchesSearch && matchesCategory
        }
        _uiState.value = HomeUIState.Success(filtered)
    }
}
