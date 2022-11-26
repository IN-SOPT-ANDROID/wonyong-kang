package com.example.data.entity.response

import com.example.data.entity.Follower
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FollowersResponse(
    val page: Int,
    @SerialName("per_page") val perPage: Int,
    val total: Int,
    @SerialName("total_pages") val totalPages: Int,
    val data: List<Follower>
)
