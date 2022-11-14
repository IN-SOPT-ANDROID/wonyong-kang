package com.example.data.datasource.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.entity.Follower
import com.example.data.service.MainService
import retrofit2.HttpException
import java.io.IOException
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
        val nextPageNumber = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = mainService.getFollowers(nextPageNumber)
            LoadResult.Page(
                data = response.data,
                prevKey = null,
                nextKey = if (nextPageNumber < 4) nextPageNumber + 1 else null
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }
}
