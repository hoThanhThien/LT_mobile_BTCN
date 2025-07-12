package com.example.uthsmarttasks.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.uthsmarttasks.auth.FirebaseAuthManager
import com.example.uthsmarttasks.ui.screens.LoginScreen
import com.example.uthsmarttasks.ui.screens.ProfileScreen
import com.example.uthsmarttasks.ui.screens.HomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // Check if user is already signed in
    LaunchedEffect(Unit) {
        if (FirebaseAuthManager.isUserSignedIn()) {
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        composable("home") {
            HomeScreen(
                onProfileClick = {
                    navController.navigate("profile")
                },
                onSignOut = {
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            )
        }

        composable("profile") {
            ProfileScreen(
                onSignOut = {
                    navController.navigate("login") {
                        popUpTo("profile") { inclusive = true }
                    }
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}