package com.practice.lokaljobs.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.practice.lokaljobs.utils.constants.Constants

@Entity(tableName = Constants.ROOM_DB_V3_TABLE_NAME)
data class V3(
    @PrimaryKey(autoGenerate = true) val v3_id: Int,
    val field_key: String,
    val field_name: String,
    val field_value: String
){
    constructor() : this(
        v3_id = 0,
        field_key = "",
        field_name = "",
        field_value = ""
    )
}