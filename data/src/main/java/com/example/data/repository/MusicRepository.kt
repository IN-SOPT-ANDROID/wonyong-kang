package com.example.data.repository

import com.example.data.entity.Music

interface MusicRepository {
    suspend fun getMusics(): Result<List<Music>>
}
