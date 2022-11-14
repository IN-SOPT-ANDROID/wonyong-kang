package com.example.data.repository.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.datasource.remote.ReqResPagingSource
import com.example.data.entity.Follower
import com.example.data.repository.MainRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class MainRepositoryImpl @Inject constructor(
    private val reqResPagingSource: ReqResPagingSource
) : MainRepository {
    override fun getFollowersStream(): Flow<PagingData<Follower>> =
        Pager(
            config = PagingConfig(pageSize = 3),
            pagingSourceFactory = { reqResPagingSource }
        ).flow
}
