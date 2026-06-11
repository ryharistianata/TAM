package com.example.tam.ui.screen.favorite

import androidx.compose.runtime.Composable
import com.example.tam.data.model.response.VolunteerResponse

@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel,
    onNavigateToDetail: (String) -> Unit
) {
    FavoriteContent(
        uiState = viewModel.uiState.value,
        onItemClick = { activity ->
            onNavigateToDetail(activity.id)
        }
    )
}
