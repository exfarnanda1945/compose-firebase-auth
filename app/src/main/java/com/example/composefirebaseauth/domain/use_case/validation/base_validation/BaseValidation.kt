package com.example.composefirebaseauth.domain.use_case.validation.base_validation

import android.util.Patterns
import com.example.composefirebaseauth.domain.use_case.validation.ValidationResult

open class BaseValidation: IBaseValidation() {
    override fun emailValidation(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                isSuccess = false,
                errorMessage = "This email can't blank"
            )
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                isSuccess = false,
                errorMessage = "Invalid email"
            )
        }

        return ValidationResult(true)
    }
}