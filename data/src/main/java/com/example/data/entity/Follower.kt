package com.example.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Follower(
    val id: Int,
    @SerialName("avatar_url") val avatar: String,
    @SerialName("login") val name: String
)
