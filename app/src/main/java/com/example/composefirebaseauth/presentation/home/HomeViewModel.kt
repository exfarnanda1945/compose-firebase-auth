package com.example.composefirebaseauth.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composefirebaseauth.domain.repository.IAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: IAuthRepository
) : ViewModel() {
    val currentUser = authRepository.currentUser

    fun onEvent(event: HomeEvent){
        when(event){
            HomeEvent.OnLogout -> viewModelScope.launch {
                authRepository.logout()
            }
        }
    }
}