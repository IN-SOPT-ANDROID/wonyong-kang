package com.example.data.repository

import com.example.data.entity.response.SignInResponse
import com.example.data.entity.response.SignUpResponse

interface AuthRepository {
    fun isAutoLogin(): Boolean
    fun setAutoLogin(isAutoLogin: Boolean)
    suspend fun postSignIn(
        email: String,
        password: String
    ): Result<SignInResponse>

    suspend fun postSignUp(
        email: String,
        password: String,
        name: String
    ): Result<SignUpResponse>
}
