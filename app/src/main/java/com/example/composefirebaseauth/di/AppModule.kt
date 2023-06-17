package com.example.composefirebaseauth.di

import com.example.composefirebaseauth.data.repository.AuthRepository
import com.example.composefirebaseauth.domain.repository.IAuthRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object AppModule {
    @Provides
    fun provideAuthRepository(auth: FirebaseAuth): IAuthRepository = AuthRepository(auth)
}