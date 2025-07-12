package com.mb_an.bt2buoi4.ui

import AuthViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun HomeScreen(viewModel: AuthViewModel, onLogout: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Welcome!", fontSize = 28.sp)
        Button(onClick = {
            viewModel.logout()
            onLogout()
        }) {
            Text("Logout")
        }
    }
}
