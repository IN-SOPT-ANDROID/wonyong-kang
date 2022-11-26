package com.example.data.datasource.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.entity.Follower
import com.example.data.service.MainService
import javax.inject.Inject

class ReqResPagingSource @Inject constructor(
    private val mainService: MainService
) : PagingSource<Int, Follower>() {
    override fun getRefreshKey(state: PagingState<Int, Follower>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Follower> {
        val key = params.key ?: STARTING_PAGE_INDEX
        return runCatching {
            val response = mainService.getFollowers(key).data
            LoadResult.Page(
                data = response,
                prevKey = if (key == STARTING_PAGE_INDEX) null else key - 1,
                nextKey = if (response.isEmpty()) null else key + 1
            )
        }.getOrElse {
            LoadResult.Error(it)
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }
}
