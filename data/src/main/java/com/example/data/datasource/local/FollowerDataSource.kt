package com.example.data.datasource.local

import android.content.Context
import android.util.Log
import com.example.data.entity.Follower
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class FollowerDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun getFollowers(): Flow<List<Follower>> = flow {
        getJsonFollowers()
            .catch { exception -> Log.e("FollowerDataSource", "error : $exception") }
            .collect { jsonFollowers ->
                val followers = mutableListOf<Follower>()
                val json = Json { ignoreUnknownKeys = true }
                json.decodeFromString<List<Follower>>(jsonFollowers)
                    .forEach { follower -> followers.add(follower) }
                emit(followers)
            }
    }

    private fun getJsonFollowers(): Flow<String> = flow {
        val jsonFollowers = context.assets.open("follower_list.json")
            .bufferedReader()
            .use { it.readText() }
        emit(jsonFollowers)
    }
        .catch { emit("error!!! : ${it.message}") }
}
