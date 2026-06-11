package com.example.tam.ui.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
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
fun DetailContent(
    activity: VolunteerResponse,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize().background(Color(0xFFF5F7FA))) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 80.dp) // Space for bottom button
        ) {
            // Large Image
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFFE8F0FE)
            ) {
                AsyncImage(
                    model = activity.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = activity.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Surface(
                    color = Color(0xFFE8F0FE),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = "Volunteer",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontSize = 12.sp,
                        color = Color(0xFF1967D2),
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                DetailSection(title = "Lokasi", content = activity.location)
                DetailSection(title = "Tanggal", content = activity.date)
                DetailSection(title = "Deskripsi Kegiatan", content = activity.description)
                
                // Extra sections like in the reference image
                DetailSection(title = "Persyaratan", content = "Membawa semangat positif dan perlengkapan kebersihan pribadi.")
            }
        }

        // Bottom Button "Tambah ke Favorit"
        Button(
            onClick = { /* Tambah ke Favorit */ },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp)
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1967D2))
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.FavoriteBorder, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Tambah ke Favorit", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun DetailSection(title: String, content: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = title + ":",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.Black
        )
        Text(
            text = content,
            fontSize = 14.sp,
            color = Color.DarkGray,
            lineHeight = 20.sp
        )
    }
}
