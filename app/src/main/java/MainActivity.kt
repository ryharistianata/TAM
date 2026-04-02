package com.example.tam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tam.model.VolunteerEvent
import com.example.tam.model.VolunteerSource
import com.example.tam.ui.theme.TAMTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TAMTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    // 1. State Management: Menggunakan remember dan mutableStateListOf agar state tetap terjaga
    val eventList = remember { mutableStateListOf(*VolunteerSource.dummyEvent.toTypedArray()) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            HeaderSection()
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        HomeScreen(
            events = eventList,
            onToggleFavorite = { id ->
                val index = eventList.indexOfFirst { it.id == id }
                if (index != -1) {
                    eventList[index] = eventList[index].copy(isFavorite = !eventList[index].isFavorite)
                }
            },
            onJoinEvent = { id ->
                val index = eventList.indexOfFirst { it.id == id }
                if (index != -1) {
                    eventList[index] = eventList[index].copy(isJoined = !eventList[index].isJoined)
                }
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun HeaderSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(top = 48.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
    ) {
        Text(
            text = "Gerak Alam",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun HomeScreen(
    events: List<VolunteerEvent>,
    onToggleFavorite: (Int) -> Unit,
    onJoinEvent: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        // Section Rekomendasi (LazyRow)
        item {
            Text(
                text = "Rekomendasi Kegiatan",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(events.take(3)) { event ->
                    EventRowItem(event = event, onToggleFavorite = { onToggleFavorite(event.id) })
                }
            }
        }

        // Section Daftar Utama (LazyColumn)
        item {
            Text(
                text = "Daftar Kegiatan Utama",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 12.dp)
            )
        }

        items(events) { event ->
            EventCard(
                event = event,
                onToggleFavorite = { onToggleFavorite(event.id) },
                onJoin = { onJoinEvent(event.id) },
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}

@Composable
fun EventRowItem(
    event: VolunteerEvent,
    onToggleFavorite: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.width(220.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column {
            Box {
                Image(
                    painter = painterResource(id = event.imageRes),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().height(120.dp),
                    contentScale = ContentScale.Crop
                )
                FavoriteButton(
                    isFavorite = event.isFavorite,
                    onToggle = onToggleFavorite,
                    modifier = Modifier.align(Alignment.TopEnd).padding(4.dp)
                )
            }
            Text(
                text = event.namaKegiatan,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun EventCard(
    event: VolunteerEvent,
    onToggleFavorite: () -> Unit,
    onJoin: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            Box {
                Image(
                    painter = painterResource(id = event.imageRes),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().height(180.dp),
                    contentScale = ContentScale.Crop
                )
                FavoriteButton(
                    isFavorite = event.isFavorite,
                    onToggle = onToggleFavorite,
                    modifier = Modifier.align(Alignment.TopEnd).padding(8.dp)
                )
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = event.namaKegiatan,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = event.lokasi, style = MaterialTheme.typography.bodySmall)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = event.deskripsi,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onJoin,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (event.isJoined) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = if (event.isJoined) "Joined" else "Join",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun FavoriteButton(
    isFavorite: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onToggle,
        modifier = modifier
            .background(Color.White.copy(alpha = 0.7f), shape = CircleShape)
            .size(32.dp)
    ) {
        Icon(
            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = "Favorite",
            tint = if (isFavorite) Color.Red else Color.Gray,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHome() {
    TAMTheme {
        MainScreen()
    }
}