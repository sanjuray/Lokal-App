package com.practice.lokaljobs.storage.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.practice.lokaljobs.converters.JobTagConverter
import com.practice.lokaljobs.converters.V3Converter
import com.practice.lokaljobs.model.ContactPreference
import com.practice.lokaljobs.model.ContentV3
import com.practice.lokaljobs.model.JobTag
import com.practice.lokaljobs.model.PrimaryDetails
import com.practice.lokaljobs.model.Result
import com.practice.lokaljobs.model.V3
import com.practice.lokaljobs.storage.dao.BookmarkDAO
import com.practice.lokaljobs.utils.constants.Constants

@Database(entities = [Result::class, ContactPreference::class, ContentV3::class, JobTag::class, PrimaryDetails::class, V3::class], version = 1, exportSchema = false)
@TypeConverters(V3Converter::class,JobTagConverter::class)
abstract class LokalRoomDatabase: RoomDatabase(){

    companion object {
        @Volatile
        private var dbInstance: LokalRoomDatabase? = null

        fun getInstance(context: Context): LokalRoomDatabase {
            if (dbInstance == null) {
                synchronized(LokalRoomDatabase::class.java) {
                    if (dbInstance == null) {
                        dbInstance = Room.databaseBuilder(
                            context.applicationContext,
                            LokalRoomDatabase::class.java,
                            Constants.ROOM_DB_NAME
                        )
                        .fallbackToDestructiveMigration()
                        .build()
                    }
                }
            }
            return dbInstance!!
        }
    }


    abstract fun bookmarkDao(): BookmarkDAO

}