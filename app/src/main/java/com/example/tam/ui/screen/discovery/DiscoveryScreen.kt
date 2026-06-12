package com.example.tam.ui.screen.discovery

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tam.ui.theme.KitabisaCyan

@Composable
fun DiscoveryScreen() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Eksplorasi Aksi",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold,
                color = KitabisaCyan
            )
            Text(
                text = "Temukan inspirasi aksi nyata sesuai minatmu",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }

        val categories = listOf(
            DiscoveryCat("Lingkungan", Icons.Default.Eco, Color(0xFFE8F5E9), Color(0xFF2E7D32)),
            DiscoveryCat("Pendidikan", Icons.Default.School, Color(0xFFE3F2FD), Color(0xFF1565C0)),
            DiscoveryCat("Sosial", Icons.Default.Groups, Color(0xFFFFF3E0), Color(0xFFE65100)),
            DiscoveryCat("Bencana", Icons.Default.HealthAndSafety, Color(0xFFFFEBEE), Color(0xFFC62828)),
            DiscoveryCat("Budaya", Icons.Default.TheaterComedy, Color(0xFFF3E5F5), Color(0xFF7B1FA2)),
            DiscoveryCat("Teknologi", Icons.Default.Terminal, Color(0xFFE0F2F1), Color(0xFF00695C))
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(categories) { cat ->
                CategoryCard(cat, onActionClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/results?search_query=volunteer+${cat.title}"))
                    context.startActivity(intent)
                })
            }
        }
    }
}

data class DiscoveryCat(val title: String, val icon: ImageVector, val bgColor: Color, val iconColor: Color)

@Composable
fun CategoryCard(cat: DiscoveryCat, onActionClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().height(160.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                modifier = Modifier.size(48.dp),
                shape = RoundedCornerShape(14.dp),
                color = cat.bgColor
            ) {
                Icon(
                    imageVector = cat.icon,
                    contentDescription = null,
                    tint = cat.iconColor,
                    modifier = Modifier.padding(12.dp)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = cat.title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onActionClick,
                modifier = Modifier.height(32.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
                colors = ButtonDefaults.buttonColors(containerColor = KitabisaCyan),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Lihat Video", fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
