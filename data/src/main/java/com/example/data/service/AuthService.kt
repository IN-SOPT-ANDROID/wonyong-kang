package com.example.data.service

import com.example.data.entity.request.SignInRequest
import com.example.data.entity.request.SignUpRequest
import com.example.data.entity.response.SignInResponse
import com.example.data.entity.response.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/api/user/signin")
    suspend fun postSignIn(@Body body: SignInRequest): SignInResponse

    @POST("/api/user/signup")
    suspend fun postSignUp(@Body body: SignUpRequest): SignUpResponse
}
