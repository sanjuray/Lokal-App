package com.practice.lokaljobs.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.practice.lokaljobs.model.Result
import com.practice.lokaljobs.network.RetrofitServices

import com.practice.lokaljobs.utils.constants.Constants
import com.practice.lokaljobs.utils.errorLogs

class JobsPagingSource(
    private val apiService: RetrofitServices
): PagingSource<Int, Result>() {

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try{
            val nextPage = params.key ?: Constants.FIRST_PAGE_INDEX
            val response = apiService.getJobs(nextPage)
            LoadResult.Page(
                data = response.results,
                prevKey = null,
                nextKey = nextPage.plus(1)
            )
        }catch (e: Exception){
            e.errorLogs("load@JobPaginSource")
            LoadResult.Error(e)
        }
    }

}