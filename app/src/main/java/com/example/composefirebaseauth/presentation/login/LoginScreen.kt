package com.example.composefirebaseauth.presentation.login

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.composefirebaseauth.presentation.components.DialogLoading
import com.example.composefirebaseauth.presentation.components.PasswordOutlineTextField
import com.example.composefirebaseauth.presentation.components.SingleLineOutlineTextField
import com.example.composefirebaseauth.utils.UiEvent
import com.example.composefirebaseauth.utils.navigation.Routes

@Composable
fun LoginScreen(navHostController: NavHostController) {
    val loginViewModel = hiltViewModel<LoginViewModel>()
    val state = loginViewModel.formState
    val context = LocalContext.current
    var loading by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        loginViewModel.loginChannel.collect { uiEvent ->
            when (uiEvent) {
                UiEvent.OnValidationSuccess -> Toast.makeText(
                    context,
                    "Success",
                    Toast.LENGTH_SHORT
                )
                    .show()

                is UiEvent.Navigate -> navHostController.navigate(uiEvent.route)
                is UiEvent.OnError -> {
                    Toast.makeText(context, uiEvent.error, Toast.LENGTH_SHORT)
                        .show()
                    loading = false
                }
                UiEvent.OnLoading -> loading = true
                is UiEvent.OnSuccess -> {
                    Toast.makeText(context, "Success Login !", Toast.LENGTH_SHORT).show()
                    loading = false
                    navHostController.navigate(Routes.HomeScreen.route){
                        popUpTo(Routes.LoginScreen.route){
                            inclusive = true
                        }
                    }
                }

                else -> {}
            }
        }
    }

    if (loading) {
        DialogLoading()
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Login Form",
                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
            )
            Spacer(modifier = Modifier.height(16.dp))

            SingleLineOutlineTextField(
                value = state.email,
                onValueChanged = { loginViewModel.onEvent(LoginEvent.OnChangeEmail(it))},
                label = "Email",
                isError = state.emailError != null,
                supportingText = state.emailError ?: ""
            )
            Spacer(modifier = Modifier.height(16.dp))

            PasswordOutlineTextField(
                value = state.password,
                onValueChanged ={
                    loginViewModel.onEvent(LoginEvent.OnChangePassword(it))
                } ,
                label ="Password" ,
                isError = state.passwordError != null,
                supportingText = state.passwordError ?: ""
            )
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    loginViewModel.onEvent(LoginEvent.OnSubmit)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            ) {
                Text(text = "Login")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(text = "Don't have account?")
                Text(
                    text = " Sign Up",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable {
                        loginViewModel.onEvent(LoginEvent.NavigateToSignUpScreen)
                    })
            }
        }
    }

}
