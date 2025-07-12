package com.example.uthsmarttasks.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.uthsmarttasks.ui.navigation.AppNavigation
import com.example.uthsmarttasks.ui.theme.UTHSmartTasksTheme
import com.example.uthsmarttasks.utils.GoogleSignInHelper

@Suppress("DEPRECATION")
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Google Sign In
        GoogleSignInHelper.initGoogleSignIn(this)

        setContent {
            UTHSmartTasksTheme {
                AppNavigation()
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        GoogleSignInHelper.handleSignInResult(this, requestCode, resultCode, data)
    }


}