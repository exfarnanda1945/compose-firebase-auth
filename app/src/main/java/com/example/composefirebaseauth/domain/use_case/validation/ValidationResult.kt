package com.example.composefirebaseauth.domain.use_case.validation

data class ValidationResult(
    val isSuccess: Boolean,
    val errorMessage: String? = null
)