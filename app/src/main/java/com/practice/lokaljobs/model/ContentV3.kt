package com.practice.lokaljobs.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.practice.lokaljobs.converters.V3Converter
import com.practice.lokaljobs.utils.constants.Constants

@Entity(tableName = Constants.ROOM_DB_CONTENT_V3_TABLE_NAME)
data class ContentV3(
    @PrimaryKey(autoGenerate = true)
    val content_v3_id: Int = 0,
    @TypeConverters(V3Converter::class)
    val V3: List<V3> = listOf()
)