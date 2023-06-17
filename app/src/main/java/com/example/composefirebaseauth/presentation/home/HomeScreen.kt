package com.example.composefirebaseauth.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.composefirebaseauth.utils.navigation.Routes

@Composable
fun HomeScreen(navHostController: NavHostController) {
    val homeViewModel = hiltViewModel<HomeViewModel>()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Welcome to the home, ${homeViewModel.currentUser?.displayName}")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                homeViewModel.onEvent(HomeEvent.OnLogout)
                navHostController.navigate(Routes.LoginScreen.route){
                    popUpTo(Routes.HomeScreen.route){
                        inclusive = true
                    }
                }
            }) {
                Text(text = "Logout")
            }
        }

    }
}
