package com.example.tam.ui.screen.achievement

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tam.ui.theme.KitabisaCyan
import com.example.tam.ui.theme.KitabisaOrange

@Composable
fun AchievementScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Pencapaian Kamu",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold,
                color = KitabisaCyan
            )
            Text(
                text = "Koleksi lencana bukti aksi nyatamu",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }

        val badges = listOf(
            BadgeData("Relawan Hijau", "Menanam 10+ pohon", Icons.Default.Verified, Color(0xFFE8F5E9), Color(0xFF4CAF50)),
            BadgeData("Pahlawan Pangan", "Donasi 5+ paket makanan", Icons.Default.Star, Color(0xFFFFF3E0), KitabisaOrange),
            BadgeData("Guru Inspiratif", "Mengajar di 3+ desa", Icons.Default.MilitaryTech, Color(0xFFE3F2FD), Color(0xFF2196F3)),
            BadgeData("Pejuang Laut", "Aksi bersih pantai 1x", Icons.Default.EmojiEvents, Color(0xFFF3E5F5), Color(0xFF9C27B0))
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(badges) { badge ->
                BadgeCard(badge)
            }
        }
    }
}

data class BadgeData(val name: String, val desc: String, val icon: ImageVector, val bgColor: Color, val iconColor: Color)

@Composable
fun BadgeCard(data: BadgeData) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                modifier = Modifier.size(64.dp).clip(CircleShape),
                color = data.bgColor
            ) {
                Icon(
                    imageVector = data.icon,
                    contentDescription = null,
                    tint = data.iconColor,
                    modifier = Modifier.padding(16.dp)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = data.name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(text = data.desc, fontSize = 11.sp, color = Color.Gray, modifier = Modifier.padding(top = 4.dp))
        }
    }
}
