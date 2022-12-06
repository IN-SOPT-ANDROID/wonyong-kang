package com.example.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class Music(
    val id: Int,
    val image: String,
    val title: String,
    val singer: String
)
