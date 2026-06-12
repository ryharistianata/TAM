package com.example.tam.ui.screen.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.tam.data.model.response.VolunteerResponse
import com.example.tam.ui.screen.favorite.FavoriteViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    activity: VolunteerResponse?,
    favoriteViewModel: FavoriteViewModel,
    onBack: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Kegiatan") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        if (activity == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("Data tidak ditemukan")
            }
        } else {
            DetailContent(
                activity = activity,
                modifier = Modifier.padding(paddingValues),
                onAddToFavorite = {
                    favoriteViewModel.addFavorite(activity)
                    scope.launch {
                        snackbarHostState.showSnackbar("Berhasil ditambahkan ke favorit!")
                    }
                }
            )
        }
    }
}
