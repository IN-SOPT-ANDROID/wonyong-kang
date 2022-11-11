package com.example.data.datasource.remote

import com.example.data.service.MainService
import javax.inject.Inject

class MainDataSource @Inject constructor(
    private val mainService: MainService
) {
    suspend fun getFollowers(page: Int) = mainService.getFollowers(page)
}
