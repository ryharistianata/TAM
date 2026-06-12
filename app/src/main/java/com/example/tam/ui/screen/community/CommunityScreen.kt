package com.example.tam.ui.screen.community

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tam.ui.theme.KitabisaCyan

@Composable
fun CommunityScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Komunitas Alam",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold,
                color = KitabisaCyan
            )
            IconButton(onClick = {}) {
                Icon(Icons.Default.Search, contentDescription = null, tint = KitabisaCyan)
            }
        }

        // Community Groups List
        val communities = listOf(
            CommunityData("Sahabat Mangrove", "1.2k Anggota", "Fokus pada pelestarian pesisir pantai."),
            CommunityData("Relawan Kebersihan Kota", "850 Anggota", "Aksi bersih-bersih rutin setiap minggu."),
            CommunityData("Edukasi Hijau Muda", "500 Anggota", "Mendidik generasi muda cinta lingkungan."),
            CommunityData("Penyelamat Satwa", "2.1k Anggota", "Membantu satwa liar yang terluka atau terancam.")
        )

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(communities) { community ->
                CommunityCard(community)
            }
        }
    }
}

data class CommunityData(val name: String, val members: String, val desc: String)

@Composable
fun CommunityCard(data: CommunityData) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(56.dp).clip(CircleShape),
                color = KitabisaCyan.copy(alpha = 0.1f)
            ) {
                Icon(
                    imageVector = Icons.Default.Groups,
                    contentDescription = null,
                    tint = KitabisaCyan,
                    modifier = Modifier.padding(14.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(text = data.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = data.members, fontSize = 12.sp, color = KitabisaCyan, fontWeight = FontWeight.Medium)
                Text(text = data.desc, fontSize = 12.sp, color = Color.Gray, maxLines = 1)
            }
            
            Button(
                onClick = {},
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = KitabisaCyan),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                modifier = Modifier.height(36.dp)
            ) {
                Text("Gabung", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
