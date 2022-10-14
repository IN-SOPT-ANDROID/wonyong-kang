package com.example.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val pw: String,
    val mbti: String
)
