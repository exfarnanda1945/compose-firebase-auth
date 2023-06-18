package com.example.composefirebaseauth.presentation.sign_up

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

@Composable
fun SignUpScreen(navHostController: NavHostController) {
    val signUpViewModel = hiltViewModel<SignUpViewModel>()
    val context = LocalContext.current
    val formState = signUpViewModel.formState
    var loading by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = true) {
        signUpViewModel.signUpChannel.collect {
            when (it) {
                UiEvent.OnValidationSuccess -> Toast.makeText(
                    context,
                    "Sign Up Success",
                    Toast.LENGTH_SHORT
                ).show()

                UiEvent.PopBackStack -> navHostController.popBackStack()
                is UiEvent.OnError -> {
                    Toast.makeText(context,it.error,Toast.LENGTH_SHORT).show()
                    loading = false
                }
                UiEvent.OnLoading -> loading = true
                is UiEvent.OnSuccess -> {
                    Toast.makeText(context,"Success Register",Toast.LENGTH_SHORT).show()
                    loading = false
                    navHostController.popBackStack()
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
            .padding(64.dp), contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Registration Form",
                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
            )
            Spacer(modifier = Modifier.height(16.dp))

            SingleLineOutlineTextField(
                value = formState.name,
                onValueChanged = {signUpViewModel.onEvent(SignUpEvent.OnNameChange(it))},
                label = "Name",
                isError = formState.nameError != null,
                supportingText = formState.nameError ?: ""
            )
            Spacer(modifier = Modifier.height(4.dp))

            SingleLineOutlineTextField(
                value = formState.email,
                onValueChanged = { signUpViewModel.onEvent(SignUpEvent.OnEmailChange(it))},
                label = "Email",
                isError = formState.emailError != null,
                supportingText = formState.emailError ?: ""
            )
            Spacer(modifier = Modifier.height(4.dp))

            PasswordOutlineTextField(
                value = formState.password,
                onValueChanged ={signUpViewModel.onEvent(SignUpEvent.OnPasswordChange(it))} ,
                label ="Password" ,
                isError = formState.passwordError != null,
                supportingText = formState.passwordError ?: ""
            )
            Spacer(modifier = Modifier.height(4.dp))

            PasswordOutlineTextField(
                value = formState.confirmPassword,
                onValueChanged ={
                    signUpViewModel.onEvent(SignUpEvent.OnConfirmPasswordChange(it))
                } ,
                label ="Confirm Password" ,
                isError = formState.confirmPasswordError != null,
                supportingText = formState.confirmPasswordError ?: ""
            )
            Spacer(modifier = Modifier.height(4.dp))

            Button(
                onClick = {
                    signUpViewModel.onEvent(SignUpEvent.OnSubmit)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            ) {
                Text(text = "Sign Up")
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(text = "Have an account?")
                Text(
                    text = " Login",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable {
                        signUpViewModel.onEvent(SignUpEvent.NavigateToLoginScreen)
                    })
            }
        }
    }

}
