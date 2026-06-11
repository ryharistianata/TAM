package com.example.tam.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.tam.data.model.response.VolunteerResponse

@Composable
fun HomeContent(
    activities: List<VolunteerResponse>,
    onItemClick: (VolunteerResponse) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF5F7FA))) {
        // Header Biru
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1967D2), RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                .padding(16.dp)
        ) {
            Text("Selamat Datang,", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
            Text("Relawan Alam", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Search Bar
            OutlinedTextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("Cari event volunteer...", color = Color.Gray) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray) },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                )
            )
        }

        // Filter Kategori (Dummy All, A, B, C...)
        LazyRow(
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val categories = listOf("All", "A", "B", "C", "D", "E", "F")
            items(categories) { cat ->
                Surface(
                    color = if (cat == "All") Color(0xFF1967D2) else Color.White,
                    shape = RoundedCornerShape(8.dp),
                    shadowElevation = 2.dp,
                    onClick = {}
                ) {
                    Text(
                        cat, 
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        color = if (cat == "All") Color.White else Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // List Kegiatan
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(activities) { item ->
                VolunteerCard(item, onClick = { onItemClick(item) })
            }
        }
    }
}

@Composable
fun VolunteerCard(activity: VolunteerResponse, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Gambar di Kiri
            Surface(
                modifier = Modifier.size(60.dp),
                shape = RoundedCornerShape(8.dp),
                color = Color(0xFFE8F0FE)
            ) {
                AsyncImage(
                    model = activity.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            // Teks di Tengah
            Column(modifier = Modifier.weight(1f)) {
                Text(activity.title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF1967D2))
                Text(activity.description, maxLines = 1, fontSize = 12.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(4.dp))
                Surface(color = Color(0xFFE8F0FE), shape = RoundedCornerShape(4.dp)) {
                    Text("Volunteer", modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp), fontSize = 10.sp, color = Color(0xFF1967D2))
                }
            }
            
            // Heart Icon di Kanan
            Icon(Icons.Default.FavoriteBorder, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(24.dp))
        }
    }
}
