package com.practice.lokaljobs.network

import com.practice.lokaljobs.model.Results
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitServices {
    @GET("common/jobs/")
    suspend fun getJobs(
        @Query("page") page: Int
    ): Results
}