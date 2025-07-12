package com.mb_an.bt2buoi4.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.mb_an.bt2buoi4.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(viewModel: AuthViewModel, onRegisterSuccess: () -> Unit, onGoToLogin: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val authState by viewModel.authState.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Register", fontSize = 28.sp)
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password") }, visualTransformation = PasswordVisualTransformation())
        Button(onClick = { viewModel.register(email, password) }, modifier = Modifier.fillMaxWidth()) {
            Text("Register")
        }
        TextButton(onClick = onGoToLogin) {
            Text("Already have an account? Login")
        }
        when (authState) {
            is `AuthState.kt`.Loading -> CircularProgressIndicator()
            is `AuthState.kt`.Success -> onRegisterSuccess()
            is `AuthState.kt`.Error -> Text((authState as `AuthState.kt`.Error).message, color = Color.Red)
            else -> {}
        }
    }
}
