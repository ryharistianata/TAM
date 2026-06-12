package com.example.tam.ui.screen.reward

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tam.ui.theme.KitabisaCyan
import com.example.tam.ui.theme.KitabisaOrange

@Composable
fun RewardScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
    ) {
        Text(
            text = "Hadiah & Apresiasi",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.ExtraBold,
            color = KitabisaCyan,
            modifier = Modifier.padding(16.dp)
        )

        val rewards = listOf(
            RewardItem("Sertifikat Relawan Hijau", "Telah menanam 20+ pohon", "Lencana", Icons.Default.WorkspacePremium, KitabisaOrange),
            RewardItem("Voucher Kopi Teman Alam", "Diskon 50% di Kafe Partner", "Kupon", Icons.Default.CardGiftcard, KitabisaCyan),
            RewardItem("Relawan Teladan Bulan Ini", "Keaktifan di 5+ aksi sosial", "Gelar", Icons.Default.WorkspacePremium, Color(0xFF4CAF50))
        )

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(rewards) { item ->
                RewardCard(item)
            }
        }
    }
}

data class RewardItem(val title: String, val desc: String, val type: String, val icon: androidx.compose.ui.graphics.vector.ImageVector, val color: Color)

@Composable
fun RewardCard(data: RewardItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(56.dp),
                shape = RoundedCornerShape(16.dp),
                color = data.color.copy(alpha = 0.1f)
            ) {
                Icon(data.icon, contentDescription = null, tint = data.color, modifier = Modifier.padding(14.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = data.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = data.desc, fontSize = 12.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {},
                    modifier = Modifier.height(32.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = data.color),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Klaim Hadiah", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
