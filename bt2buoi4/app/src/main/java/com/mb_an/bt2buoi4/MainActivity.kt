package com.mb_an.bt2buoi4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.FirebaseApp
import com.mb_an.bt2buoi4.ui.HomeScreen
import com.mb_an.bt2buoi4.ui.LoginScreen
import com.mb_an.bt2buoi4.ui.RegisterScreen
import com.mb_an.bt2buoi4.viewmodel.AuthViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        val viewModel = AuthViewModel()

        setContent {
            var currentScreen by remember { mutableStateOf("login") }

            when (currentScreen) {
                "login" -> LoginScreen(
                    viewModel,
                    onLoginSuccess = { currentScreen = "home" },
                    onGoToRegister = { currentScreen = "register" }
                )
                "register" -> RegisterScreen(
                    viewModel,
                    onRegisterSuccess = { currentScreen = "home" },
                    onGoToLogin = { currentScreen = "login" }
                )
                "home" -> HomeScreen(
                    viewModel,
                    onLogout = { currentScreen = "login" }
                )
            }
        }
    }
}
