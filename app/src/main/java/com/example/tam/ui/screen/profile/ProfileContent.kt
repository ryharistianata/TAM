package com.example.tam.ui.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tam.ui.theme.KitabisaCyan

@Composable
fun ProfileContent(
    uiState: ProfileUIState,
    onLogoutClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onUpdateProfile: (String, String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var isEditMode by remember { mutableStateOf(false) }
    var name by remember(uiState.name) { mutableStateOf(uiState.name) }
    var email by remember(uiState.email) { mutableStateOf(uiState.email) }
    var phone by remember(uiState.phone) { mutableStateOf(uiState.phone) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        // Header with Gradient
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(KitabisaCyan, KitabisaCyan.copy(alpha = 0.7f))
                    )
                )
        ) {
            // Action Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { 
                    if (isEditMode) {
                        onUpdateProfile(name, email, phone)
                        isEditMode = false
                    } else {
                        isEditMode = true
                    }
                }) {
                    Icon(
                        imageVector = if (isEditMode) Icons.Default.Check else Icons.Default.Edit,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
                
                IconButton(onClick = onSettingsClick) {
                    Icon(Icons.Default.Settings, contentDescription = null, tint = Color.White)
                }
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Surface(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape),
                    color = Color.White,
                    shadowElevation = 8.dp
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(60.dp).padding(16.dp),
                        tint = KitabisaCyan
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = uiState.name,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Relawan Gerak Alam",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.9f)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-30).dp)
                .background(
                    MaterialTheme.colorScheme.background,
                    RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                )
                .padding(24.dp)
        ) {
            Text(
                text = if (isEditMode) "Edit Profil" else "Informasi Akun",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            
            Spacer(modifier = Modifier.height(24.dp))

            if (isEditMode) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nama Lengkap") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Nomor Telepon") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = { 
                        onUpdateProfile(name, email, phone)
                        isEditMode = false
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = KitabisaCyan)
                ) {
                    Text("Simpan Perubahan", fontWeight = FontWeight.Bold)
                }
            } else {
                ProfileInfoItem(icon = Icons.Default.Person, label = "Nama Lengkap", value = uiState.name)
                ProfileInfoItem(icon = Icons.Default.Email, label = "Email", value = uiState.email)
                ProfileInfoItem(icon = Icons.Default.Phone, label = "Nomor Telepon", value = uiState.phone)

                Spacer(modifier = Modifier.height(40.dp))

                // Logout Button
                Button(
                    onClick = onLogoutClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFEBEE),
                        contentColor = Color.Red
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text("Keluar dari Akun", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(60.dp))
    }
}

@Composable
fun ProfileInfoItem(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.size(48.dp),
            shape = RoundedCornerShape(12.dp),
            color = KitabisaCyan.copy(alpha = 0.1f)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.padding(12.dp),
                tint = KitabisaCyan
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = label, fontSize = 12.sp, color = Color.Gray)
            Text(
                text = value,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}
