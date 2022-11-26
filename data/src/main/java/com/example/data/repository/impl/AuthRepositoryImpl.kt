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
    private val autoLoginDataSource: AutoLoginDataSource
) : AuthRepository {
    override fun isAutoLogin(): Boolean = autoLoginDataSource.isAutoLogin
    override fun setAutoLogin(isAutoLogin: Boolean) {
        autoLoginDataSource.isAutoLogin = isAutoLogin
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
