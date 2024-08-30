package com.practice.lokaljobs.storage.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.practice.lokaljobs.model.Result
import com.practice.lokaljobs.utils.constants.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDAO {

    @Insert
    suspend fun addBookmark(job: Result)

    @Delete
    suspend fun deleteBookmark(job: Result)

    @Query("SELECT * FROM ${Constants.ROOM_DB_BOOKMARKS_TABLE_NAME} where id=:jobId")
    fun checkBookmark(jobId: Int): Flow<List<Result>>

    @Query("SELECT * FROM ${Constants.ROOM_DB_BOOKMARKS_TABLE_NAME} order by db_added_id desc")
    fun getBookmarks(): Flow<List<Result>>

}