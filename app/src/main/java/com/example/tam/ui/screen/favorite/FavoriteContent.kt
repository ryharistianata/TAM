package com.example.tam.ui.screen.favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tam.data.model.response.VolunteerResponse
import com.example.tam.ui.screen.home.HomeContent

@Composable
fun FavoriteContent(
    uiState: FavoriteUIState,
    onItemClick: (VolunteerResponse) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (uiState) {
            is FavoriteUIState.Empty -> {
                Text(text = "Belum ada kegiatan favorit")
            }
            is FavoriteUIState.Success -> {
                HomeContent(
                    activities = uiState.data,
                    onItemClick = onItemClick
                )
            }
        }
    }
}
