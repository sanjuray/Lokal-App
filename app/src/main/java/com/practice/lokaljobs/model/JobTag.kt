package com.practice.lokaljobs.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.practice.lokaljobs.utils.constants.Constants

@Entity(tableName = Constants.ROOM_DB_JOB_TAG_TABLE_NAME)
data class JobTag(
    @PrimaryKey(autoGenerate = true)
    val job_tag_id: Int = 0,
    val value: String = ""
)