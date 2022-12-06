package com.example.data.service

import com.example.data.entity.response.MusicResponse
import retrofit2.http.GET

interface MusicService {
    @GET("/music/list")
    suspend fun getMusics(): MusicResponse
}
