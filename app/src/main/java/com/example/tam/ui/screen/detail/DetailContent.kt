package com.example.tam.ui.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
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
import com.example.tam.ui.theme.KitabisaCyan

@Composable
fun DetailContent(
    activity: VolunteerResponse,
    modifier: Modifier = Modifier,
    onAddToFavorite: () -> Unit
) {
    Box(modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
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
                    .height(300.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(24.dp),
                shadowElevation = 8.dp,
                color = MaterialTheme.colorScheme.surface
            ) {
                AsyncImage(
                    model = activity.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }

            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                Text(
                    text = activity.title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Surface(
                    color = KitabisaCyan.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Volunteer Active",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        fontSize = 12.sp,
                        color = KitabisaCyan,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                DetailSection(title = "Lokasi", content = activity.location)
                DetailSection(title = "Tanggal", content = activity.date)
                
                Divider(modifier = Modifier.padding(vertical = 16.dp), color = MaterialTheme.colorScheme.outlineVariant)
                
                Text(
                    text = "Tentang Kegiatan",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = activity.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                    lineHeight = 24.sp
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                DetailSection(title = "Persyaratan", content = "Membawa semangat positif dan perlengkapan kebersihan pribadi.")
                
                Spacer(modifier = Modifier.height(32.dp))
            }
        }

        // Single Bottom Button "Tambah ke Favorit" - Styled with kitabisacyan
        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 16.dp
        ) {
            Button(
                onClick = onAddToFavorite,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = KitabisaCyan)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Favorite, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Tambah ke Favorit", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    }
}

@Composable
fun DetailSection(title: String, content: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = content,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
            lineHeight = 22.sp
        )
    }
}
