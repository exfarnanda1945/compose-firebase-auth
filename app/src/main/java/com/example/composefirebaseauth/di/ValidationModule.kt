package com.example.composefirebaseauth.di

import com.example.composefirebaseauth.domain.use_case.validation.base_validation.BaseValidation
import com.example.composefirebaseauth.domain.use_case.validation.base_validation.IBaseValidation
import com.example.composefirebaseauth.domain.use_case.validation.login_validation.ILoginValidation
import com.example.composefirebaseauth.domain.use_case.validation.login_validation.LoginValidation
import com.example.composefirebaseauth.domain.use_case.validation.sign_up_validaton.ISignUpValidation
import com.example.composefirebaseauth.domain.use_case.validation.sign_up_validaton.SignUpValidation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
object ValidationModule {
    @Provides
    fun providesBaseValidation(): IBaseValidation = BaseValidation()

    @Provides
    fun providesSignUpValidation(): ISignUpValidation = SignUpValidation()

    @Provides
    fun providesLoginValidation():ILoginValidation = LoginValidation()
}