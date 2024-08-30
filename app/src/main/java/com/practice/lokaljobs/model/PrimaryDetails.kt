package com.practice.lokaljobs.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.practice.lokaljobs.utils.constants.Constants

@Entity(tableName = Constants.ROOM_DB_PRIMARY_DETAILS_NAME)
data class PrimaryDetails(
    @PrimaryKey(autoGenerate = true)
    val primary_details_id: Int = 0,
    val Experience: String = "",
    val Fees_Charged: String = "",
    val Job_Type: String = "",
    val Place: String = "",
    val Qualification: String = "",
    val Salary: String = ""
)