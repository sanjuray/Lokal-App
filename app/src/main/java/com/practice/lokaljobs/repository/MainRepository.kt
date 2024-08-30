package com.practice.lokaljobs.repository

import android.content.Context
import com.practice.lokaljobs.model.Result
import com.practice.lokaljobs.network.RetrofitAPI
import com.practice.lokaljobs.network.RetrofitServices
import com.practice.lokaljobs.storage.database.LokalRoomDatabase
import com.practice.lokaljobs.utils.constants.Constants
import kotlinx.coroutines.flow.Flow

class MainRepository{

    private lateinit var getLokalRoomService: LokalRoomDatabase

    fun setLokalRoomService(context: Context){
        getLokalRoomService = LokalRoomDatabase.getInstance(context)
    }

    fun getLokalService(): RetrofitServices = RetrofitAPI.apiService

    fun getBookmarks(): Flow<List<Result>>{
        if (::getLokalRoomService.isInitialized) {
            return getLokalRoomService.bookmarkDao().getBookmarks()
        } else {
            throw IllegalStateException(Constants.ERROR_MESSAGE)
        }
    }

    fun checkBookmarks(jobId: Int): Flow<List<Result>>{
        if (::getLokalRoomService.isInitialized) {
            return getLokalRoomService.bookmarkDao().checkBookmark(jobId)
        } else {
            throw IllegalStateException(Constants.ERROR_MESSAGE)
        }
    }

    suspend fun addBookmark(job: Result) {
        if (::getLokalRoomService.isInitialized) {
            getLokalRoomService.bookmarkDao().addBookmark(job)
        } else {
            throw IllegalStateException(Constants.ERROR_MESSAGE)
        }
    }

    suspend fun deleteBookmark(job: Result){
        if(::getLokalRoomService.isInitialized){
            getLokalRoomService.bookmarkDao().deleteBookmark(job)
        }else{
            throw IllegalStateException(Constants.ERROR_MESSAGE)
        }
    }

}