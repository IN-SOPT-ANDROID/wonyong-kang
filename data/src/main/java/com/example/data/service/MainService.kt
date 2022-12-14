package com.example.data.service

import com.example.data.entity.response.FollowersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MainService {
    @GET("users")
    suspend fun getFollowers(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 30
    ): FollowersResponse
}
