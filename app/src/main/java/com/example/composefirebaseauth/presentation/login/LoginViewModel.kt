package com.example.composefirebaseauth.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composefirebaseauth.data.model.Login
import com.example.composefirebaseauth.domain.repository.IAuthRepository
import com.example.composefirebaseauth.domain.use_case.validation.base_validation.IBaseValidation
import com.example.composefirebaseauth.domain.use_case.validation.login_validation.ILoginValidation
import com.example.composefirebaseauth.utils.Resource
import com.example.composefirebaseauth.utils.UiEvent
import com.example.composefirebaseauth.utils.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: IAuthRepository,
    private val loginValidation: ILoginValidation,
    private val baseValidation: IBaseValidation
) : ViewModel() {
    var formState by mutableStateOf(LoginFormState())

    private var _loginChannel = Channel<UiEvent>()
    val loginChannel = _loginChannel.receiveAsFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnChangeEmail -> {
                formState = formState.copy(email = event.email)
            }

            is LoginEvent.OnChangePassword -> {
                formState = formState.copy(password = event.password)
            }

            LoginEvent.OnSubmit -> submitData()
            LoginEvent.NavigateToSignUpScreen -> sendUiEvent(UiEvent.Navigate(Routes.SignUpScreen.route))
        }
    }

    private fun submitData() {
        val emailValidation = baseValidation.emailValidation(formState.email)
        val passwordValidation = loginValidation.passwordValidation(formState.password)

        val isInvalid = listOf(emailValidation,passwordValidation).any{ !it.isSuccess}

        if (isInvalid) {
            formState = formState.copy(
                emailError = emailValidation.errorMessage,
                passwordError = passwordValidation.errorMessage
            )
            return
        } else {
            formState = formState.copy(
                emailError = null,
                passwordError = null
            )
        }

        viewModelScope.launch {
            val login = Login(
                email = formState.email,
                password = formState.password
            )
            authRepository.login(login).collect { result ->
                when (result) {
                    is Resource.Error -> sendUiEvent(UiEvent.OnError(result.message.toString()))
                    is Resource.Loading -> sendUiEvent(UiEvent.OnLoading)
                    is Resource.Success -> sendUiEvent(UiEvent.OnSuccess())
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _loginChannel.send(event)
        }
    }

}