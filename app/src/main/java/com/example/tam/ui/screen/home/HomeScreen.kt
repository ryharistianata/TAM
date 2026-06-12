package com.example.tam.ui.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.tam.data.model.response.VolunteerResponse
import com.example.tam.ui.component.ErrorView
import com.example.tam.ui.component.LoadingView

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToDetail: (String) -> Unit
) {
    val uiState by viewModel.uiState
    val searchQuery by viewModel.searchQuery
    val selectedCategory by viewModel.selectedCategory

    Box(modifier = Modifier.fillMaxSize()) {
        when (val state = uiState) {
            is HomeUIState.Loading -> LoadingView()
            is HomeUIState.Success -> {
                HomeContent(
                    activities = state.data,
                    searchQuery = searchQuery,
                    selectedCategory = selectedCategory,
                    onSearchQueryChanged = viewModel::onSearchQueryChanged,
                    onCategorySelected = viewModel::onCategorySelected,
                    onItemClick = { activity: VolunteerResponse ->
                        onNavigateToDetail(activity.id)
                    },
                    onReload = { viewModel.fetchActivities() }
                )
            }
            is HomeUIState.Error -> {
                ErrorView(
                    message = state.message,
                    onRetry = { viewModel.fetchActivities() }
                )
            }
        }
    }
}
