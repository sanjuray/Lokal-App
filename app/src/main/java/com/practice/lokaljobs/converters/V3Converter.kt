package com.practice.lokaljobs.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.practice.lokaljobs.model.V3
import java.io.Serializable
import java.lang.reflect.Type


class V3Converter: Serializable{

    @TypeConverter
    fun fromV3List(v3List: List<V3>?): String? {
        if (v3List == null) {
            return (null)
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<V3>>() {}.type
        val json: String = gson.toJson(v3List, type)
        return json
    }

    @TypeConverter
    fun toV3List(v3String: String?): List<V3>? {
        if (v3String == null) {
            return (null)
        }
        val gson = Gson()
        val type = object : TypeToken<List<V3>>() {
        }.type
        val v3List =
            gson.fromJson<List<V3>>(v3String, type)
        return v3List
    }
}