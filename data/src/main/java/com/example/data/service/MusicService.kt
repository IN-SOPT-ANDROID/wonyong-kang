package com.example.data.service

import com.example.data.entity.response.MusicResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface MusicService {
    @GET("/music/list")
    suspend fun getMusics(): MusicResponse

    @Multipart
    @POST("/music")
    suspend fun postMusic(
        @Part("request") request: RequestBody,
        @Part image: MultipartBody.Part
    )
}
