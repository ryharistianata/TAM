package com.example.tam.ui.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun ProfileContent(
    uiState: ProfileUIState,
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FA))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Profil Pengguna",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1967D2),
            modifier = Modifier.padding(vertical = 16.dp)
        )

        // Avatar
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color(0xFFE8F0FE)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(android.R.drawable.ic_menu_gallery), // Placeholder icon
                contentDescription = null,
                modifier = Modifier.size(60.dp),
                tint = Color(0xFF1967D2)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Input Fields
        ProfileTextField(label = "Nama", value = uiState.name)
        Spacer(modifier = Modifier.height(16.dp))
        ProfileTextField(label = "Email", value = uiState.email)
        Spacer(modifier = Modifier.height(16.dp))
        ProfileTextField(label = "Password Baru (Opsional)", value = "")

        Spacer(modifier = Modifier.height(32.dp))

        // Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedButton(
                onClick = { /* Batal */ },
                modifier = Modifier.weight(1f).height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Batal")
            }
            Button(
                onClick = { /* Simpan */ },
                modifier = Modifier.weight(1f).height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1967D2))
            ) {
                Text("Simpan")
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Logout
        TextButton(
            onClick = onLogoutClick,
            modifier = Modifier.padding(bottom = 32.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                    contentDescription = null,
                    tint = Color.Red
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Keluar dari Akun", color = Color.Red, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun ProfileTextField(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            readOnly = true // In this dummy view
        )
    }
}
