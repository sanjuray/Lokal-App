package com.practice.lokaljobs.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.practice.lokaljobs.utils.constants.Constants

@Entity(tableName = Constants.ROOM_DB_CONTACT_PREFERENCE_TABLE_NAME)
data class ContactPreference(
    @PrimaryKey(autoGenerate = true)
    val contact_preference_id: Int = 0,
    val preference: Int = 0,
    val preferred_call_end_time: String = "",
    val preferred_call_start_time: String = "",
    val whatsapp_link: String = ""
)