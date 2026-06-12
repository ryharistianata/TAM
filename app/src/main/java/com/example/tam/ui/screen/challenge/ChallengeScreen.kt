package com.example.tam.ui.screen.challenge

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Grass
import androidx.compose.material.icons.filled.Recycling
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tam.ui.theme.KitabisaCyan
import com.example.tam.ui.theme.KitabisaOrange

@Composable
fun ChallengeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Tantangan Relawan",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold,
                color = KitabisaCyan
            )
            Text(
                text = "Selesaikan misi dan kumpulkan poin aksi!",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }

        val challenges = listOf(
            ChallengeData("Pejuang Bebas Plastik", "Kurangi penggunaan plastik selama 7 hari berturut-turut.", 0.6f, Icons.Default.Recycling, "6/7 hari"),
            ChallengeData("Penanam Pohon Muda", "Ikuti minimal 3 kegiatan penanaman pohon bulan ini.", 0.33f, Icons.Default.Grass, "1/3 aksi"),
            ChallengeData("Hemat Energi", "Matikan alat elektronik yang tidak dipakai selama 24 jam.", 0.0f, Icons.Default.ElectricBolt, "0/24 jam"),
            ChallengeData("Penjaga Sumber Air", "Bersihkan area sekitar sungai atau pantai terdekat.", 1.0f, Icons.Default.WaterDrop, "Selesai")
        )

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(challenges) { item ->
                ChallengeCard(item)
            }
        }
    }
}

data class ChallengeData(val title: String, val desc: String, val progress: Float, val icon: ImageVector, val progressText: String)

@Composable
fun ChallengeCard(data: ChallengeData) {
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
                color = if (data.progress >= 1f) Color(0xFFE8F5E9) else KitabisaCyan.copy(alpha = 0.1f)
            ) {
                Icon(
                    imageVector = data.icon,
                    contentDescription = null,
                    tint = if (data.progress >= 1f) Color(0xFF388E3C) else KitabisaCyan,
                    modifier = Modifier.padding(14.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(text = data.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = data.desc, fontSize = 12.sp, color = Color.Gray, lineHeight = 16.sp)
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    LinearProgressIndicator(
                        progress = { data.progress },
                        modifier = Modifier.weight(1f).height(6.dp),
                        color = if (data.progress >= 1f) Color(0xFF4CAF50) else KitabisaCyan,
                        trackColor = Color.LightGray.copy(alpha = 0.3f),
                        strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = data.progressText, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = KitabisaCyan)
                }
            }
        }
    }
}
