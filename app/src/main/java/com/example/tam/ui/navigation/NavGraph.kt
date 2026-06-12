package com.example.tam.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tam.ui.screen.home.HomeViewModel
import com.example.tam.ui.screen.home.HomeUIState
import com.example.tam.ui.screen.login.LoginScreen
import com.example.tam.ui.screen.login.LoginViewModel
import com.example.tam.ui.screen.splash.SplashScreen
import com.example.tam.ui.screen.detail.DetailScreen
import com.example.tam.ui.screen.favorite.FavoriteViewModel
import com.example.tam.ui.screen.profile.ProfileViewModel
import com.example.tam.ui.screen.main.MainScreen
import com.example.tam.ui.screen.settings.SettingsScreen
import com.example.tam.ui.screen.leaderboard.LeaderboardScreen
import com.example.tam.ui.screen.history.HistoryScreen
import com.example.tam.ui.screen.reward.RewardScreen
import com.example.tam.ui.screen.achievement.AchievementScreen
import com.example.tam.ui.screen.education.EducationScreen
import com.example.tam.ui.screen.challenge.ChallengeScreen
import com.example.tam.ui.screen.community.CommunityScreen
import com.example.tam.ui.screen.discovery.DiscoveryScreen
import com.example.tam.ui.screen.donation.DonationScreen
import com.example.tam.ui.screen.feed.ActionFeedScreen
import com.example.tam.ui.screen.news.NewsScreen
import com.example.tam.ui.theme.ThemeViewModel

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Main : Screen("main")
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object Profile : Screen("profile")
    object Settings : Screen("settings")
    object Leaderboard : Screen("leaderboard")
    object History : Screen("history")
    object Reward : Screen("reward")
    object Achievement : Screen("achievement")
    object Education : Screen("education")
    object Challenge : Screen("challenge")
    object Community : Screen("community")
    object Discovery : Screen("discovery")
    object Donation : Screen("donation")
    object Feed : Screen("feed")
    object News : Screen("news")
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
    profileViewModel: ProfileViewModel,
    themeViewModel: ThemeViewModel
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
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Main.route) {
            MainScreen(
                homeViewModel = homeViewModel,
                favoriteViewModel = favoriteViewModel,
                profileViewModel = profileViewModel,
                themeViewModel = themeViewModel,
                onNavigateToDetail = { id ->
                    navController.navigate(Screen.Detail.createRoute(id))
                },
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onNavigateToSettings = {
                    navController.navigate(Screen.Settings.route)
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
                favoriteViewModel = favoriteViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                themeViewModel = themeViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
