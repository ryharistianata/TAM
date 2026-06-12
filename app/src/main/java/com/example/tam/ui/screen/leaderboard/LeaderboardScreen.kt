package com.example.tam.ui.screen.leaderboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tam.ui.theme.KitabisaCyan
import com.example.tam.ui.theme.KitabisaOrange

@Composable
fun LeaderboardScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header with Gradient
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(KitabisaCyan, KitabisaCyan.copy(alpha = 0.8f))
                    ),
                    shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                )
                .padding(top = 48.dp, bottom = 32.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.EmojiEvents,
                    contentDescription = null,
                    tint = KitabisaOrange,
                    modifier = Modifier.size(48.dp)
                )
                Text(
                    text = "Peringkat Relawan",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Apresiasi untuk aksi nyatamu",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }

        // List Peringkat
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val players = listOf(
                LeaderboardData("Budi Santoso", 2450, "https://i.pravatar.cc/150?u=1"),
                LeaderboardData("Siti Aminah", 2100, "https://i.pravatar.cc/150?u=2"),
                LeaderboardData("Andi Wijaya", 1950, "https://i.pravatar.cc/150?u=3"),
                LeaderboardData("Rina Pratama", 1800, "https://i.pravatar.cc/150?u=4"),
                LeaderboardData("Eko Saputra", 1650, "https://i.pravatar.cc/150?u=5"),
                LeaderboardData("Dewi Lestari", 1500, "https://i.pravatar.cc/150?u=6")
            )

            itemsIndexed(players) { index, player ->
                RankItem(rank = index + 1, data = player)
            }
        }
    }
}

data class LeaderboardData(val name: String, val points: Int, val avatar: String)

@Composable
fun RankItem(rank: Int, data: LeaderboardData) {
    val rankColor = when (rank) {
        1 -> Color(0xFFFFD700) // Gold
        2 -> Color(0xFFC0C0C0) // Silver
        3 -> Color(0xFFCD7F32) // Bronze
        else -> Color.Transparent
    }

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
            Text(
                text = "#$rank",
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                color = if (rank <= 3) KitabisaCyan else Color.Gray,
                modifier = Modifier.width(40.dp)
            )
            
            Surface(
                modifier = Modifier.size(45.dp).clip(CircleShape),
                color = KitabisaCyan.copy(alpha = 0.1f)
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.padding(8.dp),
                    tint = KitabisaCyan
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = data.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${data.points} Poin Aksi",
                    fontSize = 12.sp,
                    color = KitabisaCyan
                )
            }

            if (rank <= 3) {
                Icon(
                    imageVector = Icons.Default.EmojiEvents,
                    contentDescription = null,
                    tint = rankColor,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
