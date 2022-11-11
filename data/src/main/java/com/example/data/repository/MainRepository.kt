package com.example.data.repository

import com.example.data.entity.Follower

interface MainRepository {
    suspend fun getFollowers(page: Int): Result<List<Follower>>
}
