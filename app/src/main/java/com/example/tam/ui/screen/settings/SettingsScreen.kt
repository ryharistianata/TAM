package com.example.tam.ui.screen.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tam.ui.theme.ThemeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    themeViewModel: ThemeViewModel,
    onBack: () -> Unit
) {
    val isDarkMode by themeViewModel.isDarkMode

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pengaturan") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Tampilan", style = MaterialTheme.typography.titleLarge)
            
            ListItem(
                headlineContent = { Text("Mode Gelap") },
                supportingContent = { Text("Ubah tampilan aplikasi menjadi gelap") },
                trailingContent = { 
                    Switch(
                        checked = isDarkMode, 
                        onCheckedChange = { themeViewModel.toggleDarkMode(it) }
                    ) 
                }
            )
            
            HorizontalDivider()
            
            Text("Tentang Kami", style = MaterialTheme.typography.titleMedium)
            Text("Aplikasi Gerak Alam v1.0.0")
        }
    }
}
