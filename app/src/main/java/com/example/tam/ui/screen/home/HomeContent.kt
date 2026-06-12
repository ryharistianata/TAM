package com.example.tam.ui.screen.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.tam.R
import com.example.tam.data.model.response.VolunteerResponse
import com.example.tam.ui.theme.KitabisaCyan

@Composable
fun HomeContent(
    activities: List<VolunteerResponse>,
    searchQuery: String,
    selectedCategory: String,
    onSearchQueryChanged: (String) -> Unit,
    onCategorySelected: (String) -> Unit,
    onItemClick: (VolunteerResponse) -> Unit,
    onReload: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // 1. Premium Header Section (Seamless UI)
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = KitabisaCyan,
                shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp),
                shadowElevation = 8.dp
            ) {
                Column(
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(bottom = 24.dp)
                ) {
                    // Top Bar
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, top = 12.dp, end = 20.dp, bottom = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.app_name),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                        IconButton(onClick = onReload) {
                            Icon(Icons.Default.Refresh, contentDescription = "Reload", tint = Color.White)
                        }
                    }

                    // Welcome Content
                    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                        Text(
                            text = stringResource(R.string.welcome_back), 
                            color = Color.White.copy(alpha = 0.8f), 
                            fontSize = 14.sp
                        )
                        Text(
                            text = stringResource(R.string.relawan_alam), 
                            color = Color.White, 
                            fontSize = 24.sp, 
                            fontWeight = FontWeight.Bold
                        )
                        
                        Spacer(modifier = Modifier.height(20.dp))
                        
                        // Fungsional Search Bar
                        Surface(
                            modifier = Modifier.fillMaxWidth().height(54.dp),
                            shape = RoundedCornerShape(16.dp),
                            color = Color.White,
                            shadowElevation = 4.dp
                        ) {
                            TextField(
                                value = searchQuery,
                                onValueChange = onSearchQueryChanged,
                                placeholder = { Text(stringResource(R.string.search_placeholder), color = Color.Gray, fontSize = 14.sp) },
                                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = KitabisaCyan) },
                                modifier = Modifier.fillMaxSize(),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    cursorColor = KitabisaCyan
                                ),
                                singleLine = true
                            )
                        }
                    }
                }
            }
        }

        // 2. Horizontal Category Filter
        item {
            Column(modifier = Modifier.padding(top = 24.dp)) {
                Text(
                    text = "Kategori Pilihan",
                    modifier = Modifier.padding(horizontal = 20.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    val categories = listOf("All", "Bantu", "Ceria", "Donasi", "Edukasi", "Food")
                    items(categories) { cat ->
                        val isSelected = selectedCategory == cat
                        Surface(
                            color = if (isSelected) KitabisaCyan else MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(14.dp),
                            shadowElevation = if (isSelected) 6.dp else 2.dp,
                            onClick = { onCategorySelected(cat) },
                            border = if (!isSelected) BorderStroke(1.dp, KitabisaCyan.copy(alpha = 0.2f)) else null
                        ) {
                            Text(
                                text = cat, 
                                modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
                                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }

        // 3. Activity List Section (Clickable)
        item {
            Text(
                text = "Aksi Relawan Terbaru",
                modifier = Modifier.padding(start = 20.dp, top = 12.dp, end = 20.dp, bottom = 16.dp),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        if (activities.isEmpty()) {
            item {
                Box(modifier = Modifier.fillMaxWidth().height(300.dp), contentAlignment = Alignment.Center) {
                    Text(stringResource(R.string.search_not_found), color = Color.Gray)
                }
            }
        } else {
            items(activities) { activity ->
                Box(modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 16.dp)) {
                    VolunteerCard(activity, onClick = { onItemClick(activity) })
                }
            }
        }
        
        item { Spacer(modifier = Modifier.height(32.dp)) }
    }
}

@Composable
fun VolunteerCard(activity: VolunteerResponse, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        border = BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.1f))
    ) {
        Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            // MURNI DARI API URL
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(activity.displayImage)
                    .crossfade(true)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .build(),
                contentDescription = null,
                modifier = Modifier.size(90.dp).clip(RoundedCornerShape(18.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(activity.title, fontWeight = FontWeight.Bold, fontSize = 17.sp, color = MaterialTheme.colorScheme.onSurface)
                Text(activity.description, maxLines = 1, fontSize = 12.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(color = KitabisaCyan.copy(alpha = 0.1f), shape = RoundedCornerShape(6.dp)) {
                        Text(
                            text = "Relawan", 
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), 
                            fontSize = 10.sp, 
                            color = KitabisaCyan, 
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(activity.location, fontSize = 11.sp, color = Color.Gray)
                }
            }
            Icon(Icons.Default.FavoriteBorder, contentDescription = null, tint = KitabisaCyan.copy(alpha = 0.4f))
        }
    }
}
