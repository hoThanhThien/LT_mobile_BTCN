package com.example.uthsmarttasks.auth

import android.content.Context
import android.widget.Toast
import com.example.uthsmarttasks.utils.GoogleSignInHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

object FirebaseAuthManager {
    private val auth = FirebaseAuth.getInstance()

    fun signInWithGoogle(context: Context, onSuccess: () -> Unit, onFailure: (String) -> Unit = {}) {
        GoogleSignInHelper.launchGoogleSignIn(context as android.app.Activity) { idToken ->
            if (idToken != null) {
                val credential = GoogleAuthProvider.getCredential(idToken, null)
                auth.signInWithCredential(credential)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            onSuccess()
                        } else {
                            val errorMessage = task.exception?.message ?: "Login Failed!"
                            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                            onFailure(errorMessage)
                        }
                    }
            } else {
                val errorMessage = "Failed to get Google ID token"
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                onFailure(errorMessage)
            }
        }
    }

    fun signOut(context: Context, onComplete: () -> Unit = {}) {
        GoogleSignInHelper.signOut(context as android.app.Activity) {
            onComplete()
        }
    }

    fun getCurrentUser() = auth.currentUser

    fun isUserSignedIn() = auth.currentUser != null
}