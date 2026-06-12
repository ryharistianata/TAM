package com.example.tam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.tam.data.remote.retrofit.RetrofitClient
import com.example.tam.data.repository.VolunteerRepository
import com.example.tam.ui.navigation.NavGraph
import com.example.tam.ui.screen.home.HomeViewModel
import com.example.tam.ui.screen.login.LoginViewModel
import com.example.tam.ui.screen.favorite.FavoriteViewModel
import com.example.tam.ui.screen.profile.ProfileViewModel
import com.example.tam.ui.theme.TAMTheme
import com.example.tam.ui.theme.ThemeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeViewModel: ThemeViewModel = viewModel()
            val isDarkMode by themeViewModel.isDarkMode
            
            TAMTheme(darkTheme = isDarkMode) {
                AppMain(themeViewModel)
            }
        }
    }
}

@Composable
fun AppMain(themeViewModel: ThemeViewModel) {
    val navController = rememberNavController()

    // Dependency Injection setup (Simplified)
    val api = RetrofitClient.instance
    val repository = VolunteerRepository(api)
    
    // ViewModels initialization
    val homeViewModel: HomeViewModel = viewModel(
        factory = object : androidx.lifecycle.ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                return HomeViewModel(repository) as T
            }
        }
    )
    
    val loginViewModel: LoginViewModel = viewModel()
    val favoriteViewModel: FavoriteViewModel = viewModel()
    val profileViewModel: ProfileViewModel = viewModel()

    NavGraph(
        navController = navController,
        homeViewModel = homeViewModel,
        loginViewModel = loginViewModel,
        favoriteViewModel = favoriteViewModel,
        profileViewModel = profileViewModel,
        themeViewModel = themeViewModel
    )
}
