package com.example.data.entity

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Follower(
    val id: Int,
    @SerializedName("avatar_url") val avatar: String,
    @SerializedName("login") val name: String
)
