package com.example.composefirebaseauth.data.repository

import com.example.composefirebaseauth.data.model.Login
import com.example.composefirebaseauth.data.model.Register
import com.example.composefirebaseauth.domain.repository.IAuthRepository
import com.example.composefirebaseauth.utils.Resource
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth
) : IAuthRepository {
    override fun login(login: Login): Flow<Resource<AuthResult>> = flow {
        emit(Resource.Loading())
        val result = auth.signInWithEmailAndPassword(login.email, login.password).await()
        emit(Resource.Success(result))
    }.catch {
        emit(Resource.Error(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    override fun register(register: Register): Flow<Resource<AuthResult>> = flow {
        emit(Resource.Loading())
        val result = auth.createUserWithEmailAndPassword(register.email, register.password).await()
        emit(Resource.Success(result))
    }.catch {
        emit(Resource.Error(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    override fun logout() {
        auth.signOut()
    }

    override val currentUser: FirebaseUser?
        get() = auth.currentUser
}