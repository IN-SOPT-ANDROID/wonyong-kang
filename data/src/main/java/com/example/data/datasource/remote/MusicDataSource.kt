package com.example.data.datasource.remote

import com.example.data.service.MusicService
import javax.inject.Inject

class MusicDataSource @Inject constructor(
    private val musicService: MusicService
) {
    suspend fun getMusics() = musicService.getMusics()
}
