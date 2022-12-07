package com.example.data.datasource.remote

import com.example.data.service.MusicService
import javax.inject.Inject
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class MusicDataSource @Inject constructor(
    private val musicService: MusicService
) {
    suspend fun getMusics() = musicService.getMusics()
    suspend fun postMusic(
        title: String,
        singer: String,
        image: MultipartBody.Part
    ) {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val jsonObject = JSONObject("{\"singer\":\"$singer\",\"title\":\"$title\"}").toString()
            .toRequestBody(mediaType)
        musicService.postMusic(
            request = jsonObject,
            image = image
        )
    }
}
