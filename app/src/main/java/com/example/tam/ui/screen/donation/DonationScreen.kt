package com.example.tam.ui.screen.donation

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
import com.example.tam.ui.theme.KitabisaOrange

@Composable
fun DonationScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
    ) {
        Text(
            text = "Donasi Aksi",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.ExtraBold,
            color = KitabisaCyan,
            modifier = Modifier.padding(16.dp)
        )

        val donations = listOf(
            DonationItemData("Bantu Tanam 10.000 Mangrove", "Lembaga Hijau Alam", 0.75f, "Rp 7.500.000", "https://images.unsplash.com/photo-1581067720543-803f533bb351"),
            DonationItemData("Peralatan Bersih Sungai", "Komunitas Sungai Bersih", 0.45f, "Rp 1.200.000", "https://images.unsplash.com/photo-1532996122724-e3c354a0b15b"),
            DonationItemData("Edukasi Alam Anak Desa", "Relawan Cerdas", 0.90f, "Rp 4.500.000", "https://images.unsplash.com/photo-1509099836639-18ba1795216d")
        )

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(donations) { item ->
                DonationCard(item)
            }
        }
    }
}

data class DonationItemData(val title: String, val author: String, val progress: Float, val collected: String, val image: String)

@Composable
fun DonationCard(data: DonationItemData) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            AsyncImage(
                model = data.image,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(20.dp)) {
                Text(text = data.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(text = data.author, fontSize = 12.sp, color = Color.Gray)
                
                Spacer(modifier = Modifier.height(16.dp))
                
                LinearProgressIndicator(
                    progress = { data.progress },
                    modifier = Modifier.fillMaxWidth().height(8.dp),
                    color = KitabisaOrange,
                    trackColor = KitabisaOrange.copy(alpha = 0.1f),
                    strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Text("Terkumpul", fontSize = 12.sp, color = Color.Gray)
                    Text(data.collected, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = KitabisaCyan)
                }
            }
        }
    }
}
