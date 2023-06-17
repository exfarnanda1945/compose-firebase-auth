package com.example.composefirebaseauth.utils

sealed class UiEvent{
    object OnValidationSuccess:UiEvent()
    data class Navigate(val route:String):UiEvent()
    object PopBackStack:UiEvent()
    object OnLoading:UiEvent()
    data class OnError(val error:String):UiEvent()
    data class OnSuccess(val message:String? = null ):UiEvent()
}
