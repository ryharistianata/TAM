package com.example.tam.ui.screen.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tam.ui.theme.KitabisaCyan

@Composable
fun HistoryScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
    ) {
        Text(
            text = "Riwayat Aksi",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = KitabisaCyan,
            modifier = Modifier.padding(16.dp)
        )

        // Dummy Data Riwayat
        val historyItems = listOf(
            HistoryItemData("Penanaman 1000 Mangrove", "12 Okt 2024", "Selesai"),
            HistoryItemData("Bersih Pantai Kuta", "05 Sep 2024", "Selesai"),
            HistoryItemData("Edukasi Lingkungan SD 01", "20 Agt 2024", "Selesai"),
            HistoryItemData("Donasi Pangan Jakarta", "15 Jul 2024", "Selesai")
        )

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(historyItems) { item ->
                HistoryCard(item)
            }
        }
    }
}

data class HistoryItemData(val title: String, val date: String, val status: String)

@Composable
fun HistoryCard(item: HistoryItemData) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(48.dp),
                shape = RoundedCornerShape(12.dp),
                color = KitabisaCyan.copy(alpha = 0.1f)
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = KitabisaCyan,
                    modifier = Modifier.padding(12.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = item.title, 
                    fontWeight = FontWeight.Bold, 
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = item.date, 
                    fontSize = 13.sp, 
                    color = Color.Gray
                )
                Text(
                    text = item.status,
                    fontSize = 12.sp,
                    color = Color(0xFF4CAF50),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
