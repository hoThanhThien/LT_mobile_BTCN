package com.example.uthsmarttasks.utils

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

object GoogleSignInHelper {
    private lateinit var googleSignInClient: GoogleSignInClient
    private const val RC_SIGN_IN = 9001
    private const val TAG = "GoogleSignInHelper"

    private lateinit var auth: FirebaseAuth
    private var onSignInResult: ((String?) -> Unit)? = null

    fun initGoogleSignIn(activity: Activity) {
        auth = FirebaseAuth.getInstance()


        val webClientId = "572169045820-9f540le1fomge8520ulipc85md9j87kt.apps.googleusercontent.com" // This MUST be replaced!

        if (webClientId == "572169045820-9f540le1fomge8520ulipc85md9j87kt.apps.googleusercontent.com") {
            Log.e(TAG, "ERROR: You must replace YOUR_WEB_CLIENT_ID with your actual Web Client ID!")
            Log.e(TAG, "Get it from: Google Cloud Console > APIs & Services > Credentials")
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(webClientId)
            .requestEmail()
            .requestProfile()
            .build()

        googleSignInClient = GoogleSignIn.getClient(activity, gso)
        Log.d(TAG, "✅ Google Sign-In initialized")
    }

    fun launchGoogleSignIn(activity: Activity, onResult: (String?) -> Unit) {
        Log.d(TAG, "🚀 Launching Google Sign-In...")
        onSignInResult = onResult

        try {
            val signInIntent = googleSignInClient.signInIntent
            activity.startActivityForResult(signInIntent, RC_SIGN_IN)
        } catch (e: Exception) {
            Log.e(TAG, "Error launching Google Sign-In", e)
            onResult(null)
        }
    }

    fun handleSignInResult(activity: Activity, requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(TAG, "📱 Handling sign-in result: requestCode=$requestCode, resultCode=$resultCode")

        if (requestCode == RC_SIGN_IN) {
            try {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                val account = task.getResult(ApiException::class.java)

                if (account != null) {
                    Log.d(TAG, " Google Sign-In successful")
                    Log.d(TAG, " Email: ${account.email}")
                    Log.d(TAG, " Name: ${account.displayName}")

                    val idToken = account.idToken
                    if (idToken != null) {
                        Log.d(TAG, " ID Token obtained successfully")
                        onSignInResult?.invoke(idToken)
                    } else {
                        Log.e(TAG, " ID Token is null - Check your Web Client ID configuration")
                        Log.e(TAG, "Make sure you're using the Web Client ID, not Android Client ID")
                        onSignInResult?.invoke(null)
                    }
                } else {
                    Log.e(TAG, " Google account is null")
                    onSignInResult?.invoke(null)
                }

            } catch (e: ApiException) {
                Log.e(TAG, " Google Sign-In failed with code: ${e.statusCode}")
                when (e.statusCode) {
                    12501 -> Log.e(TAG, " User cancelled the sign-in")
                    12502 -> Log.e(TAG, " Sign-in currently in progress")
                    12500 -> Log.e(TAG, " Sign-in failed - Check your configuration")
                    else -> Log.e(TAG, " Unknown error: ${e.message}")
                }
                onSignInResult?.invoke(null)
            }
        }
    }

    fun signOut(activity: Activity, onComplete: () -> Unit) {
        Log.d(TAG, " Signing out...")
        auth.signOut()
        googleSignInClient.signOut().addOnCompleteListener(activity) { task ->
            if (task.isSuccessful) {
                Log.d(TAG, " Sign out completed")
            } else {
                Log.e(TAG, " Sign out failed", task.exception)
            }
            onComplete()
        }
    }

    fun revokeAccess(activity: Activity, onComplete: () -> Unit) {
        Log.d(TAG, "🗑 Revoking access...")
        auth.signOut()
        googleSignInClient.revokeAccess().addOnCompleteListener(activity) { task ->
            if (task.isSuccessful) {
                Log.d(TAG, " Access revoked")
            } else {
                Log.e(TAG, " Revoke access failed", task.exception)
            }
            onComplete()
        }
    }
}