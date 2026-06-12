package com.example.tam.ui.screen.impact

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tam.ui.theme.KitabisaCyan

@Composable
fun ImpactScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
    ) {
        // Impact Header Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = KitabisaCyan)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text("Level Relawan", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
                Text("Penjaga Bumi Muda", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                LinearProgressIndicator(
                    progress = { 0.65f },
                    modifier = Modifier.fillMaxWidth().height(8.dp),
                    color = Color.White,
                    trackColor = Color.White.copy(alpha = 0.3f),
                    strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
                )
                Text("650 / 1000 Poin Aksi", color = Color.White, fontSize = 12.sp, modifier = Modifier.padding(top = 8.dp))
            }
        }

        Text(
            text = "Kontribusi Nyatamu",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        val stats = listOf(
            ImpactStat("Aksi Ikut", "14", Icons.Default.VolunteerActivism, Color(0xFFE3F2FD), Color(0xFF1976D2)),
            ImpactStat("Jam Bakti", "52h", Icons.Default.Schedule, Color(0xFFFFF3E0), Color(0xFFF57C00)),
            ImpactStat("Pohon Tanam", "28", Icons.Default.Eco, Color(0xFFE8F5E9), Color(0xFF388E3C)),
            ImpactStat("Lencana", "6", Icons.Default.Star, Color(0xFFF3E5F5), Color(0xFF7B1FA2))
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(stats) { stat ->
                StatCard(stat)
            }
        }
    }
}

data class ImpactStat(val label: String, val value: String, val icon: ImageVector, val bgColor: Color, val iconColor: Color)

@Composable
fun StatCard(stat: ImpactStat) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(modifier = Modifier.size(48.dp), shape = RoundedCornerShape(12.dp), color = stat.bgColor) {
                Icon(stat.icon, contentDescription = null, tint = stat.iconColor, modifier = Modifier.padding(12.dp))
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(stat.value, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
            Text(stat.label, fontSize = 12.sp, color = Color.Gray)
        }
    }
}
