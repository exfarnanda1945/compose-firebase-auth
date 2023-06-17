package com.example.composefirebaseauth.domain.use_case.validation.base_validation

import com.example.composefirebaseauth.domain.use_case.validation.ValidationResult

abstract class IBaseValidation {
    abstract fun emailValidation(email:String):ValidationResult
}