package com.example.tam.ui.screen.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tam.ui.navigation.Screen
import com.example.tam.ui.screen.favorite.FavoriteScreen
import com.example.tam.ui.screen.home.HomeScreen
import com.example.tam.ui.screen.profile.ProfileScreen
import com.example.tam.ui.screen.discovery.DiscoveryScreen
import com.example.tam.ui.screen.feed.ActionFeedScreen
import com.example.tam.ui.screen.home.HomeViewModel
import com.example.tam.ui.screen.favorite.FavoriteViewModel
import com.example.tam.ui.screen.profile.ProfileViewModel
import com.example.tam.ui.theme.ThemeViewModel
import com.example.tam.ui.screen.leaderboard.LeaderboardScreen

@Composable
fun MainScreen(
    homeViewModel: HomeViewModel,
    favoriteViewModel: FavoriteViewModel,
    profileViewModel: ProfileViewModel,
    themeViewModel: ThemeViewModel,
    onNavigateToDetail: (String) -> Unit,
    onLogout: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                val items = listOf(
                    NavigationItem(Screen.Home.route, "Beranda", Icons.Default.Home),
                    NavigationItem(Screen.Discovery.route, "Eksplorasi", Icons.Default.Explore),
                    NavigationItem(Screen.Feed.route, "Kabar", Icons.Default.Public),
                    NavigationItem(Screen.Favorite.route, "Favorit", Icons.Default.Favorite),
                    NavigationItem(Screen.Profile.route, "Profil", Icons.Default.Person)
                )
                items.forEach { item ->
                    val selected = currentDestination?.hierarchy?.any { it.route == item.route } == true
                    NavigationBarItem(
                        icon = { 
                            Icon(
                                item.icon, 
                                contentDescription = item.label,
                                tint = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                            ) 
                        },
                        label = { 
                            Text(
                                item.label, 
                                style = MaterialTheme.typography.labelSmall,
                                color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                            ) 
                        },
                        selected = selected,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(bottom = innerPadding.calculateBottomPadding())
        ) {
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route
            ) {
                composable(Screen.Home.route) {
                    HomeScreen(viewModel = homeViewModel, onNavigateToDetail = onNavigateToDetail)
                }
                composable(Screen.Discovery.route) {
                    DiscoveryScreen()
                }
                composable(Screen.Feed.route) {
                    ActionFeedScreen()
                }
                composable(Screen.Favorite.route) {
                    FavoriteScreen(viewModel = favoriteViewModel, onNavigateToDetail = onNavigateToDetail)
                }
                composable(Screen.Profile.route) {
                    ProfileScreen(
                        viewModel = profileViewModel, 
                        onLogoutSuccess = onLogout,
                        onNavigateToSettings = onNavigateToSettings
                    )
                }
            }
        }
    }
}

data class NavigationItem(val route: String, val label: String, val icon: ImageVector)
