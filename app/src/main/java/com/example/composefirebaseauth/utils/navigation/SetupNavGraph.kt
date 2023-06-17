package com.example.composefirebaseauth.utils.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.composefirebaseauth.presentation.home.HomeScreen
import com.example.composefirebaseauth.presentation.login.LoginScreen
import com.example.composefirebaseauth.presentation.sign_up.SignUpScreen

@Composable
fun SetupNavGraph(navHostController: NavHostController,startDestination:String) {
    NavHost(navController = navHostController, startDestination = startDestination) {
        composable(Routes.LoginScreen.route) {
            LoginScreen(navHostController = navHostController)
        }
        composable(Routes.SignUpScreen.route) {
            SignUpScreen(navHostController)
        }
        composable(Routes.HomeScreen.route){
            HomeScreen(navHostController = navHostController)
        }
    }
}