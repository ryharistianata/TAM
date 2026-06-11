package com.example.tam.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tam.ui.screen.home.HomeScreen
import com.example.tam.ui.screen.home.HomeViewModel
import com.example.tam.ui.screen.home.HomeUIState
import com.example.tam.ui.screen.login.LoginScreen
import com.example.tam.ui.screen.login.LoginViewModel
import com.example.tam.ui.screen.splash.SplashScreen
import com.example.tam.ui.screen.detail.DetailScreen
import com.example.tam.ui.screen.favorite.FavoriteScreen
import com.example.tam.ui.screen.favorite.FavoriteViewModel
import com.example.tam.ui.screen.profile.ProfileScreen
import com.example.tam.ui.screen.profile.ProfileViewModel

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object Profile : Screen("profile")
    object Detail : Screen("detail/{activityId}") {
        fun createRoute(activityId: String) = "detail/$activityId"
    }
}

@Composable
fun NavGraph(
    navController: NavHostController,
    homeViewModel: HomeViewModel,
    loginViewModel: LoginViewModel,
    favoriteViewModel: FavoriteViewModel,
    profileViewModel: ProfileViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(onNavigateToLogin = {
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            })
        }

        composable(Screen.Login.route) {
            LoginScreen(
                viewModel = loginViewModel,
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                viewModel = homeViewModel,
                onNavigateToDetail = { id ->
                    navController.navigate(Screen.Detail.createRoute(id))
                }
            )
        }

        composable(Screen.Favorite.route) {
            FavoriteScreen(
                viewModel = favoriteViewModel,
                onNavigateToDetail = { id ->
                    navController.navigate(Screen.Detail.createRoute(id))
                }
            )
        }

        composable(Screen.Profile.route) {
            ProfileScreen(
                viewModel = profileViewModel,
                onLogoutSuccess = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Detail.route) { backStackEntry ->
            val activityId = backStackEntry.arguments?.getString("activityId")
            val uiState = homeViewModel.uiState.value
            val activity = if (uiState is HomeUIState.Success) {
                uiState.data.find { it.id == activityId }
            } else null

            DetailScreen(
                activity = activity,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
