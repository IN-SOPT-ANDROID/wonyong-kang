package com.example.data.repository

import androidx.paging.PagingData
import com.example.data.entity.Follower
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    fun getFollowersStream(): Flow<PagingData<Follower>>
}
