package com.example.data.repository.impl

import com.example.data.datasource.remote.MainDataSource
import com.example.data.entity.Follower
import com.example.data.repository.MainRepository
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val mainDataSource: MainDataSource
) : MainRepository {
    override suspend fun getFollowers(page: Int): Result<List<Follower>> =
        runCatching { mainDataSource.getFollowers(page).data }
}
