package com.example.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Follower(
    val id: Int = -1,
    val avatar: String = "",
    @SerialName("last_name") val lastName: String = ""
)
