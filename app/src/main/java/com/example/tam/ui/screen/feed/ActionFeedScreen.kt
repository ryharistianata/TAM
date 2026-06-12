package com.example.tam.ui.screen.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
fun ActionFeedScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
    ) {
        Text(
            text = "Kabar Aksi",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.ExtraBold,
            color = KitabisaCyan,
            modifier = Modifier.padding(16.dp)
        )

        val feedItems = listOf(
            FeedData("Andi Wijaya", "Baru saja menanam 10 bibit bakau di pesisir Surabaya! Seru banget!", "https://images.unsplash.com/photo-1542601906990-b4d3fb778b09", "2 jam lalu", 124),
            FeedData("Siti Aminah", "Membantu memilah sampah di acara CFD tadi pagi. Bumi bersih, hati senang!", "https://images.unsplash.com/photo-1532996122724-e3c354a0b15b", "5 jam lalu", 89),
            FeedData("Budi Santoso", "Edukasi anak-anak tentang pentingnya menjaga sungai. Masa depan mereka ada di tangan kita.", "https://images.unsplash.com/photo-1509099836639-18ba1795216d", "1 hari lalu", 256)
        )

        LazyColumn(
            contentPadding = PaddingValues(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(feedItems) { item ->
                FeedCard(item)
            }
        }
    }
}

data class FeedData(val user: String, val content: String, val image: String, val time: String, val likes: Int)

@Composable
fun FeedCard(data: FeedData) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(0.dp), // Full width like social feed
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(modifier = Modifier.size(40.dp).clip(CircleShape), color = KitabisaCyan.copy(alpha = 0.1f)) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(data.user.take(1), fontWeight = FontWeight.Bold, color = KitabisaCyan)
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(text = data.user, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Text(text = data.time, fontSize = 11.sp, color = Color.Gray)
                }
            }

            AsyncImage(
                model = data.image,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().height(250.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.FavoriteBorder, contentDescription = null, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(16.dp))
                    Icon(Icons.Outlined.ChatBubbleOutline, contentDescription = null, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(16.dp))
                    Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(24.dp))
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "${data.likes} dukungan", fontWeight = FontWeight.ExtraBold, fontSize = 13.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = data.content,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Divider(color = Color.LightGray.copy(alpha = 0.2f))
        }
    }
}
