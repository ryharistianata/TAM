package com.example.tam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tam.api.RetrofitInstance
import com.example.tam.model.VolunteerEvent
import com.example.tam.ui.theme.TAMTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    
    // State Management for Data, Loading, and Error
    var eventList by remember { mutableStateOf<List<VolunteerEvent>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }
    
    // State for Join button processing
    var loadingJoinId by remember { mutableStateOf<Int?>(null) }

    // Logic to load data from API
    fun loadData() {
        scope.launch {
            try {
                isLoading = true
                isError = false
                
                // Fetch data from real API using Retrofit
                val result = RetrofitInstance.api.getEvents()
                eventList = result
                
                isLoading = false
            } catch (e: Exception) {
                isLoading = false
                isError = true
            }
        }
    }

    LaunchedEffect(Unit) {
        loadData()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { HeaderSection() },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        containerColor = Color(0xFFF9F9F9)
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            when {
                isLoading -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Mengambil data Gerak Alam...", color = Color.Gray)
                    }
                }
                isError -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Gagal Memuat Data",
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.Red,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(onClick = { loadData() }) {
                            Icon(Icons.Default.Refresh, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Coba Lagi")
                        }
                    }
                }
                else -> {
                    HomeScreen(
                        events = eventList,
                        loadingId = loadingJoinId,
                        onToggleFavorite = { id ->
                            eventList = eventList.map {
                                if (it.id == id) it.copy(isFavorite = !it.isFavorite) else it
                            }
                        },
                        onJoin = { id ->
                            scope.launch {
                                val event = eventList.find { it.id == id }
                                if (event != null && !event.isJoined) {
                                    loadingJoinId = id
                                    delay(2000) // Simulated processing
                                    eventList = eventList.map {
                                        if (it.id == id) it.copy(isJoined = true) else it
                                    }
                                    loadingJoinId = null
                                    snackbarHostState.showSnackbar("Berhasil bergabung di aksi: ${event.namaKegiatan}")
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun HeaderSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(start = 16.dp, top = 48.dp, end = 16.dp, bottom = 16.dp)
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
    loadingId: Int?,
    onToggleFavorite: (Int) -> Unit,
    onJoin: (Int) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Text(
                "Rekomendasi Populer",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(events.take(3)) { event ->
                    RecommendationItem(
                        event = event,
                        onToggleFavorite = { onToggleFavorite(event.id) }
                    )
                }
            }
        }

        item {
            Text(
                "Daftar Kegiatan Utama",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp, top = 24.dp, end = 16.dp, bottom = 8.dp)
            )
        }

        items(events) { event ->
            MainEventCard(
                event = event,
                isLoading = loadingId == event.id,
                onToggleFavorite = { onToggleFavorite(event.id) },
                onJoin = { onJoin(event.id) }
            )
        }
    }
}

@Composable
fun RecommendationItem(
    event: VolunteerEvent,
    onToggleFavorite: () -> Unit
) {
    Card(
        modifier = Modifier.width(160.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column {
            Box {
                AsyncImage(
                    model = event.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(Color.LightGray),
                    contentScale = ContentScale.Crop
                )
                IconButton(
                    onClick = onToggleFavorite,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp)
                        .background(Color.White.copy(alpha = 0.7f), CircleShape)
                        .size(24.dp)
                ) {
                    Icon(
                        imageVector = if (event.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = if (event.isFavorite) Color.Red else Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    event.namaKegiatan,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    event.harga,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFFE65100),
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun MainEventCard(
    event: VolunteerEvent,
    isLoading: Boolean,
    onToggleFavorite: () -> Unit,
    onJoin: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            Box {
                AsyncImage(
                    model = event.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .background(Color.LightGray),
                    contentScale = ContentScale.Crop
                )
                IconButton(
                    onClick = onToggleFavorite,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .background(Color.White.copy(alpha = 0.7f), CircleShape)
                        .size(32.dp)
                ) {
                    Icon(
                        imageVector = if (event.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = if (event.isFavorite) Color.Red else Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    event.namaKegiatan,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = event.lokasi,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    event.deskripsi,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Biaya: ${event.harga}",
                    color = Color(0xFFE65100),
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                
                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = onJoin,
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    enabled = !isLoading && !event.isJoined,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (event.isJoined) Color(0xFF4CAF50) else Color(0xFFE65100)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Memproses...")
                    } else {
                        Text(
                            text = if (event.isJoined) "Terdaftar" else "Daftar Sekarang",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
