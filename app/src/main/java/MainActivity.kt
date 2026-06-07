package com.example.tam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tam.api.RetrofitInstance
import com.example.tam.model.VolunteerEvent
import com.example.tam.repository.VolunteerRepository
import com.example.tam.ui.VolunteerUiState
import com.example.tam.ui.VolunteerViewModel
import com.example.tam.ui.theme.TAMTheme

class MainActivity : ComponentActivity() {
    private val viewModel: VolunteerViewModel by viewModels {
        VolunteerViewModel.Factory(VolunteerRepository(RetrofitInstance.api))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TAMTheme {
                MainScreen(viewModel)
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: VolunteerViewModel) {
    val snackbarHostState = remember { SnackbarHostState() }
    val uiState = viewModel.uiState
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { HeaderSection() },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        containerColor = Color(0xFFF9F9F9)
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            when (uiState) {
                is VolunteerUiState.Loading -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Menghubungkan ke server Gerak Alam...", color = Color.Gray)
                    }
                }
                is VolunteerUiState.Error -> {
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
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Periksa koneksi internet Anda", color = Color.Gray)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.getEvents() }) {
                            Icon(Icons.Default.Refresh, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Coba Lagi")
                        }
                    }
                }
                is VolunteerUiState.Success -> {
                    HomeScreen(
                        events = uiState.events,
                        loadingJoinId = viewModel.loadingJoinId,
                        onToggleFavorite = { viewModel.toggleFavorite(it) },
                        onJoin = { id ->
                            viewModel.joinEvent(id) { message ->
                                scope.launch {
                                    snackbarHostState.showSnackbar(message)
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
    loadingJoinId: Int?,
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
                    RecommendationCard(
                        event = event,
                        onToggleFavorite = { onToggleFavorite(event.id) }
                    )
                }
            }
        }

        item {
            Text(
                "Daftar Menu Lengkap",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp, top = 24.dp, end = 16.dp, bottom = 8.dp)
            )
        }

        items(events) { event ->
            MainEventCard(
                event = event,
                isLoading = loadingJoinId == event.id,
                onToggleFavorite = { onToggleFavorite(event.id) },
                onJoin = { onJoin(event.id) }
            )
        }
    }
}

@Composable
fun RecommendationCard(event: VolunteerEvent, onToggleFavorite: () -> Unit) {
    Card(
        modifier = Modifier.width(160.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column {
            Box {
                AsyncImage(
                    model = event.img,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().height(100.dp),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(android.R.drawable.ic_menu_gallery)
                )
                IconButton(
                    onClick = onToggleFavorite,
                    modifier = Modifier.align(Alignment.TopEnd).padding(4.dp)
                        .background(Color.White.copy(alpha = 0.7f), CircleShape).size(24.dp)
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
                    event.nama ?: "",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    event.harga ?: "",
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
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            Box {
                AsyncImage(
                    model = event.img,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().height(180.dp),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(android.R.drawable.ic_menu_gallery),
                    error = painterResource(android.R.drawable.ic_dialog_alert)
                )
                IconButton(
                    onClick = onToggleFavorite,
                    modifier = Modifier.align(Alignment.TopEnd).padding(8.dp)
                        .background(Color.White.copy(alpha = 0.7f), CircleShape).size(32.dp)
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
                Text(event.nama ?: "", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                    Text(event.lokasi ?: "", style = MaterialTheme.typography.bodySmall, color = Color.Gray, modifier = Modifier.padding(start = 4.dp))
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(event.deskripsi ?: "", style = MaterialTheme.typography.bodyMedium, maxLines = 2, overflow = TextOverflow.Ellipsis)
                Text(text = "Harga: ${event.harga ?: ""}", color = Color(0xFFE65100), fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(vertical = 4.dp))
                
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
                        Text(text = if (event.isJoined) "Pesan" else "Pesan", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
