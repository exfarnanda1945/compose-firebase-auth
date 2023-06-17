package com.example.composefirebaseauth.domain.repository

import com.example.composefirebaseauth.data.model.Login
import com.example.composefirebaseauth.data.model.Register
import com.example.composefirebaseauth.utils.Resource
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface IAuthRepository {
    fun login(login:Login): Flow<Resource<AuthResult>>
    fun register(register: Register):Flow<Resource<AuthResult>>
    fun logout()
    val currentUser: FirebaseUser?
}