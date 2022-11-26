package com.example.data.datasource.remote

import com.example.data.entity.request.SignInRequest
import com.example.data.entity.request.SignUpRequest
import com.example.data.entity.response.SignInResponse
import com.example.data.entity.response.SignUpResponse
import com.example.data.service.AuthService
import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val authService: AuthService
) {
    suspend fun postSignIn(signInRequest: SignInRequest): SignInResponse =
        authService.postSignIn(signInRequest)

    suspend fun postSignUp(signUpRequest: SignUpRequest): SignUpResponse =
        authService.postSignUp(signUpRequest)
}
