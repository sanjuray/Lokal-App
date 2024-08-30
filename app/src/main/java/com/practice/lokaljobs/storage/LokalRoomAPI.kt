package com.practice.lokaljobs.storage

import android.content.Context
import androidx.room.Room
import com.practice.lokaljobs.storage.database.LokalRoomDatabase
import com.practice.lokaljobs.utils.constants.Constants

object LokalRoomAPI {

    private fun getInstance(context: Context) = Room.databaseBuilder(
        context.applicationContext,
        LokalRoomDatabase::class.java,
        Constants.ROOM_DB_NAME
    ).build()

    fun getLokalRoomAPI(context: Context) = getInstance(context)

}