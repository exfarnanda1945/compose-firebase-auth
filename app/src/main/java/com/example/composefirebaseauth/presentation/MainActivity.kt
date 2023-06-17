package com.example.composefirebaseauth.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.composefirebaseauth.ui.theme.ComposeFirebaseAuthTheme
import com.example.composefirebaseauth.utils.navigation.Routes
import com.example.composefirebaseauth.utils.navigation.SetupNavGraph
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val startDestination = if (firebaseAuth.currentUser != null) {
            Routes.HomeScreen.route
        } else {
            Routes.LoginScreen.route
        }
        setContent {
            ComposeFirebaseAuthTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navHostController = rememberNavController()
                    SetupNavGraph(
                        navHostController = navHostController,
                        startDestination = startDestination
                    )
                }
            }
        }
    }
}
