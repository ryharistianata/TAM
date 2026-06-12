package com.example.tam.ui.screen.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tam.data.model.response.VolunteerResponse
import com.example.tam.ui.screen.home.VolunteerCard
import com.example.tam.ui.theme.KitabisaCyan

@Composable
fun FavoriteContent(
    uiState: FavoriteUIState,
    onItemClick: (VolunteerResponse) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
    ) {
        // Header
        Text(
            text = "Kegiatan Favorit",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = KitabisaCyan,
            modifier = Modifier.padding(16.dp)
        )

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (uiState) {
                is FavoriteUIState.Empty -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = null,
                            modifier = Modifier.size(80.dp),
                            tint = Color.LightGray.copy(alpha = 0.5f)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            "Belum ada favorit",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Gray
                        )
                        Text(
                            "Simpan kegiatan yang kamu minati di sini",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray.copy(alpha = 0.7f)
                        )
                    }
                }
                is FavoriteUIState.Success -> {
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(uiState.data) { item ->
                            VolunteerCard(item, onClick = { onItemClick(item) })
                        }
                    }
                }
            }
        }
    }
}
