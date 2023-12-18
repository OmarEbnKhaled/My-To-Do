package com.example.mytodo.data.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.mytodo.models.TaskModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class StringListConverter {

    @TypeConverter
    fun fromStringToList(stringList: String?): List<TaskModel?>? {
        return if (stringList == null) {
            null
        } else {
            val list = object : TypeToken<List<TaskModel>>(){}.type
            Gson().fromJson(stringList, list)
        }

    }

    @TypeConverter
    fun fromListToString(list: List<TaskModel?>?): String? {
        return if (list == null) {
            null
        } else {
            return Gson().toJson(list)
        }
    }

}