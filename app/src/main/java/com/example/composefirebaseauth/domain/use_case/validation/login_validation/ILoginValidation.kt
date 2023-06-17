package com.example.composefirebaseauth.domain.use_case.validation.login_validation

import com.example.composefirebaseauth.domain.use_case.validation.ValidationResult

abstract class ILoginValidation{
    abstract fun passwordValidation(password:String):ValidationResult
}