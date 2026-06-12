package com.example.tam.ui.screen.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tam.ui.theme.KitabisaCyan

@Composable
fun NotificationScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
    ) {
        Text(
            text = "Notifikasi",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.ExtraBold,
            color = KitabisaCyan,
            modifier = Modifier.padding(16.dp)
        )

        val notifications = listOf(
            NotifData("Pendaftaran Diterima!", "Kamu berhasil bergabung di aksi 'Tanam Bakau'.", "10:30", Icons.Default.CheckCircle, Color(0xFF4CAF50)),
            NotifData("Info Kegiatan", "Aksi 'Bersih Pantai' akan dimulai dalam 2 jam.", "08:00", Icons.Default.Info, KitabisaCyan),
            NotifData("Update Peringkat", "Selamat! Kamu naik ke peringkat 5 besar minggu ini.", "Kemarin", Icons.Default.Campaign, Color(0xFFFFB000))
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(notifications) { notif ->
                NotificationItem(notif)
            }
        }
    }
}

data class NotifData(val title: String, val desc: String, val time: String, val icon: ImageVector, val color: Color)

@Composable
fun NotificationItem(notif: NotifData) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(notif.color.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(notif.icon, contentDescription = null, tint = notif.color, modifier = Modifier.size(24.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Text(text = notif.title, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Text(text = notif.time, fontSize = 11.sp, color = Color.Gray)
                }
                Text(
                    text = notif.desc,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    lineHeight = 18.sp
                )
            }
        }
        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = Color.LightGray.copy(alpha = 0.2f))
    }
}
