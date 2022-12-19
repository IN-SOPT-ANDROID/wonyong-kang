package com.example.data.repository

import com.example.data.entity.Music
import okhttp3.MultipartBody

interface MusicRepository {
    suspend fun getMusics(): Result<List<Music>>
    suspend fun postMusic(
        title: String,
        singer: String,
        image: MultipartBody.Part
    ): Result<Unit>
}
