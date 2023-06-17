package com.example.composefirebaseauth.domain.use_case.validation.login_validation

import com.example.composefirebaseauth.domain.use_case.validation.ValidationResult

class LoginValidation:ILoginValidation() {
    override fun passwordValidation(password: String): ValidationResult {
        if (password.isBlank()) {
            return ValidationResult(
                isSuccess = false,
                errorMessage = "This password can't blank"
            )
        }

        return ValidationResult(true)
    }
}