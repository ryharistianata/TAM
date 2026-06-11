package com.example.tam.ui.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.tam.ui.component.ErrorView
import com.example.tam.ui.component.LoadingView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToDetail: (String) -> Unit
) {
    val uiState = viewModel.uiState.value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gerak Alam") },
                actions = {
                    IconButton(onClick = { viewModel.fetchActivities() }) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = "Reload")
                    }
                }
            )
        }
    ) { paddingValues: PaddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (uiState) {
                is HomeUIState.Loading -> LoadingView()
                is HomeUIState.Success -> {
                    HomeContent(
                        activities = uiState.data,
                        onItemClick = { activity ->
                            onNavigateToDetail(activity.id)
                        }
                    )
                }
                is HomeUIState.Error -> {
                    ErrorView(
                        message = uiState.message,
                        onRetry = { viewModel.fetchActivities() }
                    )
                }
            }
        }
    }
}
