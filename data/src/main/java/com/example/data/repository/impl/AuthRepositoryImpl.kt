package com.example.data.repository.impl

import com.example.data.datasource.local.AutoLoginDataSource
import com.example.data.datasource.remote.AuthDataSource
import com.example.data.entity.request.SignInRequest
import com.example.data.entity.request.SignUpRequest
import com.example.data.entity.response.SignInResponse
import com.example.data.entity.response.SignUpResponse
import com.example.data.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val isAutoLoginDataSource: AutoLoginDataSource
) : AuthRepository {
    override suspend fun isAutoLogin(): Boolean = isAutoLoginDataSource.isAutoLogin
    override suspend fun setAutoLogin(isAutoLogin: Boolean) {
        isAutoLoginDataSource.isAutoLogin = isAutoLogin
    }

    override suspend fun postSignIn(email: String, password: String): Result<SignInResponse> =
        runCatching { authDataSource.postSignIn(SignInRequest(email, password)) }

    override suspend fun postSignUp(
        email: String,
        password: String,
        name: String
    ): Result<SignUpResponse> =
        runCatching { authDataSource.postSignUp(SignUpRequest(email, password, name)) }
}
