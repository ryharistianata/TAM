package com.example.tam.ui.screen.education

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircle
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
fun EducationScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
    ) {
        Text(
            text = "Edukasi Alam",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.ExtraBold,
            color = KitabisaCyan,
            modifier = Modifier.padding(16.dp)
        )

        val lessons = listOf(
            LessonData("Teknik Menanam Mangrove di Pesisir", "Video", "15 Menit", "https://images.unsplash.com/photo-1581067720543-803f533bb351"),
            LessonData("Panduan Memilah Sampah Plastik", "Artikel", "5 Menit", "https://images.unsplash.com/photo-1532996122724-e3c354a0b15b"),
            LessonData("Cara Menggalang Dana untuk Sosial", "Workshop", "1 Jam", "https://images.unsplash.com/photo-1559027615-cd2d71242e5c")
        )

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(lessons) { lesson ->
                LessonCard(lesson)
            }
        }
    }
}

data class LessonData(val title: String, val type: String, val duration: String, val image: String)

@Composable
fun LessonCard(data: LessonData) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(contentAlignment = Alignment.Center) {
                AsyncImage(
                    model = data.image,
                    contentDescription = null,
                    modifier = Modifier.size(85.dp).clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
                if (data.type == "Video") {
                    Icon(Icons.Default.PlayCircle, contentDescription = null, tint = Color.White.copy(alpha = 0.8f))
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Surface(
                    color = KitabisaCyan.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = data.type,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontSize = 10.sp,
                        color = KitabisaCyan,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = data.title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface)
                Text(text = data.duration, fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}
