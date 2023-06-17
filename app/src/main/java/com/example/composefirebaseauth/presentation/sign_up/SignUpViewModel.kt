package com.example.composefirebaseauth.presentation.sign_up

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composefirebaseauth.data.model.Register
import com.example.composefirebaseauth.data.repository.AuthRepository
import com.example.composefirebaseauth.domain.use_case.validation.base_validation.IBaseValidation
import com.example.composefirebaseauth.domain.use_case.validation.sign_up_validaton.ISignUpValidation
import com.example.composefirebaseauth.utils.Resource
import com.example.composefirebaseauth.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val baseValidation: IBaseValidation,
    private val signUpValidation: ISignUpValidation,
) : ViewModel() {

     var formState by mutableStateOf(SignUpFormState())

    private var _signUpChannel = Channel<UiEvent>()
    val signUpChannel = _signUpChannel.receiveAsFlow()

    fun onEvent(event: SignUpEvent) {
        when (event) {
            SignUpEvent.NavigateToLoginScreen -> sendUiEvent(UiEvent.PopBackStack)
            is SignUpEvent.OnConfirmPasswordChange -> {
                formState = formState.copy(
                    confirmPassword = event.confirmPassword
                )
            }

            is SignUpEvent.OnEmailChange -> {
                formState = formState.copy(
                    email = event.email
                )
            }

            is SignUpEvent.OnNameChange -> {
                formState = formState.copy(
                    name = event.name
                )
            }

            is SignUpEvent.OnPasswordChange -> {
                formState = formState.copy(
                    password = event.password
                )
            }

            SignUpEvent.OnSubmit -> submitData()
        }
    }

    private fun submitData() {
        val nameValidation = signUpValidation.nameValidation(formState.name)
        val emailValidation = baseValidation.emailValidation(formState.email)
        val passwordValidation = signUpValidation.passwordValidation(formState.password)
        val confirmPasswordValidation = signUpValidation.confirmPasswordValidation(
            formState.password,
            formState.confirmPassword
        )

        val hasError = listOf(
            nameValidation,
            emailValidation,
            passwordValidation,
            confirmPasswordValidation
        ).any { !it.isSuccess }

        if(hasError){
            formState = formState.copy(
                nameError = nameValidation.errorMessage,
                emailError = emailValidation.errorMessage,
                passwordError = passwordValidation.errorMessage,
                confirmPasswordError = confirmPasswordValidation.errorMessage
            )
            return
        }else{
            formState = formState.copy(
                nameError = null,
                emailError = null,
                passwordError = null,
                confirmPasswordError = null,
            )
        }

        viewModelScope.launch {
            val register = Register(
                name = formState.name,
                email = formState.email,
                password = formState.password,
                confirmPassword = formState.password
            )

            authRepository.register(register).collect{result ->
                Log.d("RegisterResult", "submitData: ${result.message}")
                when(result){
                    is Resource.Error -> sendUiEvent(UiEvent.OnError(result.message.toString()))
                    is Resource.Loading -> sendUiEvent(UiEvent.OnLoading)
                    is Resource.Success -> sendUiEvent(UiEvent.OnSuccess())
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _signUpChannel.send(event)
        }
    }

}