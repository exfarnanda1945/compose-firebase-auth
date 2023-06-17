package com.example.composefirebaseauth.domain.use_case.validation.sign_up_validaton

import com.example.composefirebaseauth.domain.use_case.validation.ValidationResult

abstract class ISignUpValidation {
    abstract fun nameValidation(name:String):ValidationResult
    abstract fun confirmPasswordValidation(password:String,confirmPassword:String):ValidationResult
    abstract fun passwordValidation(password: String):ValidationResult
}