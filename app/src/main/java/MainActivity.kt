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
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.tam.model.VolunteerEvent
import com.example.tam.model.VolunteerSource
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
    
    // State Management sesuai Modul 10
    var eventList by remember { mutableStateOf<List<VolunteerEvent>>(emptyList()) }
    var isInitialLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }
    var loadingJoinId by remember { mutableStateOf<Int?>(null) }

    // Memuat data secara Asynchronous
    LaunchedEffect(Unit) {
        try {
            isInitialLoading = true
            delay(2000) // Simulasi loading network
            eventList = VolunteerSource.dummyEvent
            isInitialLoading = false
        } catch (e: Exception) {
            isInitialLoading = false
            isError = true
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = { HeaderSection() },
        containerColor = Color(0xFFF9F9F9)
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            when {
                isInitialLoading -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Memuat data Gerak Alam...", color = Color.Gray)
                    }
                }
                isError -> {
                    ErrorLayout(onRetry = {
                        scope.launch {
                            isInitialLoading = true
                            isError = false
                            delay(1000)
                            eventList = VolunteerSource.dummyEvent
                            isInitialLoading = false
                        }
                    })
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
                                    delay(2000) // Simulasi proses (Modul 9)
                                    eventList = eventList.map {
                                        if (it.id == id) it.copy(isJoined = true) else it
                                    }
                                    loadingJoinId = null
                                    snackbarHostState.showSnackbar("Pendaftaran ${event.namaKegiatan} berhasil!")
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
            // FIXED: Menggunakan start, top, end, bottom untuk menghindari error padding
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
                text = "Rekomendasi Populer",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                items(events.take(3)) { event ->
                    RecommendationCard(event)
                }
            }
        }

        item {
            Text(
                text = "Daftar Menu Lengkap",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 12.dp)
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
                // MODUL 10: AsyncImage Coil
                AsyncImage(
                    model = event.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(android.R.drawable.ic_menu_gallery),
                    error = painterResource(android.R.drawable.ic_dialog_alert)
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
                Text(event.namaKegiatan, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Text(event.deskripsi, style = MaterialTheme.typography.bodyMedium, color = Color.Gray, maxLines = 2, overflow = TextOverflow.Ellipsis)
                Text(text = "Biaya: ${event.harga}", color = Color(0xFFE65100), fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(vertical = 4.dp))
                
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
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Memproses...")
                    } else {
                        Text(text = if (event.isJoined) "Terdaftar" else "Pesan Sekarang", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun RecommendationCard(event: VolunteerEvent) {
    Card(
        modifier = Modifier.width(160.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column {
            AsyncImage(
                model = event.imageUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().height(100.dp),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(android.R.drawable.ic_menu_gallery)
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(event.namaKegiatan, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(event.harga, style = MaterialTheme.typography.bodySmall, color = Color(0xFFE65100), fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Composable
fun ErrorLayout(onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Gagal Memuat Data", style = MaterialTheme.typography.headlineSmall, color = Color.Red, fontWeight = FontWeight.Bold)
        Button(onClick = onRetry, modifier = Modifier.padding(top = 16.dp)) { Text("Coba Lagi") }
    }
}
