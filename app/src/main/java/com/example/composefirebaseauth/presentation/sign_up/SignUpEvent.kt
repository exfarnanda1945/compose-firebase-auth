package com.example.composefirebaseauth.presentation.sign_up

sealed class SignUpEvent{
    data class OnNameChange(val name:String):SignUpEvent()
    data class OnEmailChange(val email:String):SignUpEvent()
    data class OnPasswordChange(val password:String):SignUpEvent()
    data class OnConfirmPasswordChange(val confirmPassword:String):SignUpEvent()
    object NavigateToLoginScreen:SignUpEvent()
    object OnSubmit:SignUpEvent()
}
