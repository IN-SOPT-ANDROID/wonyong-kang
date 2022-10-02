package com.example.data.entity

import java.io.Serializable

data class User(
    val id: String,
    val pw: String,
    val mbti: String
) : Serializable
