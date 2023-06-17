package com.example.composefirebaseauth.utils.navigation

sealed class Routes( val route:String){
    object LoginScreen: Routes("login_screen")
    object SignUpScreen: Routes("sign_up_screen")
    object HomeScreen:Routes("home_screen")
}

