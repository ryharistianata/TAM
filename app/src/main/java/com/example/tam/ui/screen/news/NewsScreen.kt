package com.example.tam.ui.screen.news

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.tam.ui.theme.KitabisaCyan

@Composable
fun NewsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
    ) {
        Text(
            text = "Berita & Inspirasi",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = KitabisaCyan,
            modifier = Modifier.padding(16.dp)
        )

        val newsList = listOf(
            NewsData("Cara Efektif Mengurangi Sampah Plastik di Rumah", "Lingkungan", "5 mnt baca", "https://images.unsplash.com/photo-1532996122724-e3c354a0b15b"),
            NewsData("Kisah Relawan Muda yang Menanam 5000 Pohon", "Inspirasi", "8 mnt baca", "https://images.unsplash.com/photo-1542601906990-b4d3fb778b09"),
            NewsData("10 Lokasi Konservasi Penyu Terbaik di Indonesia", "Wisata Alam", "10 mnt baca", "https://images.unsplash.com/photo-1437622368342-7a3d73a34c8f")
        )

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(newsList) { news ->
                NewsCard(news)
            }
        }
    }
}

data class NewsData(val title: String, val category: String, val time: String, val image: String)

@Composable
fun NewsCard(news: NewsData) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            AsyncImage(
                model = news.image,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Surface(
                    color = KitabisaCyan.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = news.category,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontSize = 11.sp,
                        color = KitabisaCyan,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = news.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = news.time,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}
