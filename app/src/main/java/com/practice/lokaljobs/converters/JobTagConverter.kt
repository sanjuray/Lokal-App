package com.practice.lokaljobs.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.practice.lokaljobs.model.JobTag
import java.io.Serializable
import java.lang.reflect.Type

class JobTagConverter: Serializable {

    @TypeConverter
    fun fromJobTagList(jobTagList: List<JobTag>?): String?{
        if (jobTagList == null) {
            return (null)
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<JobTag>>() {}.type
        val json: String = gson.toJson(jobTagList, type)
        return json
    }

    @TypeConverter
    fun toJobTagList(jobTagString: String?): List<JobTag>? {
        if (jobTagString == null) {
            return (null)
        }
        val gson = Gson()
        val type = object : TypeToken<List<JobTag>>() {
        }.type
        val jobTagList =
            gson.fromJson<List<JobTag>>(jobTagString, type)
        return jobTagList
    }

}