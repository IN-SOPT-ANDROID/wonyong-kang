package com.example.data.entity.response

import com.example.data.entity.User
import kotlinx.serialization.Serializable

@Serializable
data class SignUpResponse(
    val status: Int,
    val message: String,
    val newUser: User
)
