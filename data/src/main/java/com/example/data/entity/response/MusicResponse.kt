package com.example.data.entity.response

import com.example.data.entity.Music
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MusicResponse(
    val statusCode: Int,
    val success: Boolean,
    val message: String,
    @SerialName("data") val musics: List<Music>
)
