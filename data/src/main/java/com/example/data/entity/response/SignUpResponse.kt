package com.example.data.entity.response

import com.example.data.entity.User

data class SignUpResponse(
    val status: Int,
    val message: String,
    val newUser: User
)
