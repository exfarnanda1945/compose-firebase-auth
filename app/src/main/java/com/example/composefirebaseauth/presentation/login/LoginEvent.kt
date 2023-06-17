package com.example.composefirebaseauth.presentation.login

sealed class LoginEvent{
    data class OnChangeEmail(val email:String):LoginEvent()
    data class OnChangePassword(val password:String):LoginEvent()
    object OnSubmit:LoginEvent()
    object NavigateToSignUpScreen:LoginEvent()
}
