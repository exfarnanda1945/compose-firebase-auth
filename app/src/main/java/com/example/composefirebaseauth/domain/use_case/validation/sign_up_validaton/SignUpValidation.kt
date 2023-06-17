package com.example.composefirebaseauth.domain.use_case.validation.sign_up_validaton

import com.example.composefirebaseauth.domain.use_case.validation.ValidationResult

class SignUpValidation : ISignUpValidation() {
    override fun nameValidation(name: String): ValidationResult {
        if (name.isBlank()) {
            return ValidationResult(false, "Name can't blank")
        }

        return ValidationResult(true)
    }

    override fun confirmPasswordValidation(
        password: String,
        confirmPassword: String
    ): ValidationResult {
        if(password != confirmPassword){
            return ValidationResult(false,"Confirm password doesn't match with password ")
        }

        return ValidationResult(true)
    }

    override fun passwordValidation(password: String): ValidationResult {
        if (password.isBlank()) {
            return ValidationResult(
                isSuccess = false,
                errorMessage = "This password can't blank"
            )
        }

        if (password.length < 6) {
            return ValidationResult(
                isSuccess = false,
                errorMessage = "This password needs to consists of at least 6 character"
            )
        }

        val containsLetterAndDigit = password.any{ it.isDigit() } && password.any { it.isLetter() }
        if(!containsLetterAndDigit){
            return ValidationResult(
                isSuccess = false,
                errorMessage = "This password needs to contain at least one letter and digit"
            )
        }

        return ValidationResult(true)
    }
}