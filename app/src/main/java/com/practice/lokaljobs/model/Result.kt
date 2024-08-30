package com.practice.lokaljobs.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.practice.lokaljobs.converters.JobTagConverter
import com.practice.lokaljobs.utils.constants.Constants
import java.io.Serializable

@Entity(tableName = Constants.ROOM_DB_BOOKMARKS_TABLE_NAME)
data class Result(
    var is_bookmarked: Boolean = false,
    @PrimaryKey(autoGenerate = true)
    val db_added_id: Int = 0,
    val company_name: String = "",
    @Embedded
    val contact_preference: ContactPreference = ContactPreference(),
    @Embedded
    val contentV3: ContentV3 = ContentV3(),
    val created_on: String = "",
    val custom_link: String = "",
    val id: Int = 0,
    val job_role: String = "",
    @TypeConverters(JobTagConverter::class)
    val job_tags: List<JobTag> = listOf(),
    val other_details: String = "",
    @Embedded
    val primary_details: PrimaryDetails = PrimaryDetails(),
    val shares: Int = 0,
    val title: String = "",
    val views: Int = 0
) : Serializable