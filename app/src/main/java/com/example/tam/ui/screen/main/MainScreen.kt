package com.example.tam.ui.screen.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
import com.example.tam.ui.screen.home.HomeViewModel
import com.example.tam.ui.screen.favorite.FavoriteViewModel
import com.example.tam.ui.screen.profile.ProfileViewModel

@Composable
fun MainScreen(
    homeViewModel: HomeViewModel,
    favoriteViewModel: FavoriteViewModel,
    profileViewModel: ProfileViewModel,
    onNavigateToDetail: (String) -> Unit,
    onLogout: () -> Unit
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        bottomBar = {
            NavigationBar {
                val items = listOf(
                    Triple(Screen.Home.route, "Beranda", Icons.Default.Home),
                    Triple(Screen.Favorite.route, "Favorit", Icons.Default.Favorite),
                    Triple(Screen.Profile.route, "Profil", Icons.Default.Person)
                )
                items.forEach { (route, label, icon) ->
                    NavigationBarItem(
                        icon = { Icon(icon, contentDescription = label) },
                        label = { Text(label) },
                        selected = currentDestination?.hierarchy?.any { it.route == route } == true,
                        onClick = {
                            navController.navigate(route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(viewModel = homeViewModel, onNavigateToDetail = onNavigateToDetail)
            }
            composable(Screen.Favorite.route) {
                FavoriteScreen(viewModel = favoriteViewModel, onNavigateToDetail = onNavigateToDetail)
            }
            composable(Screen.Profile.route) {
                ProfileScreen(viewModel = profileViewModel, onLogoutSuccess = onLogout)
            }
        }
    }
}
