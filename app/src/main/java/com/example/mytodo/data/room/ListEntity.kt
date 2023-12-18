package com.example.mytodo.data.room

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.mytodo.R
import com.example.mytodo.data.converters.StringListConverter
import com.example.mytodo.models.TaskModel

@Entity(tableName = "lists")
data class ListEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "list_name")
    val listName: String,

    @ColumnInfo(name = "list_icon")
    val icon: String = "list",

    @ColorRes
    @ColumnInfo(name = "color_theme")
    val colorTheme: Int,

    @ColumnInfo(name = "number_of_task")
    val numberOfTasks: Int = 0,

    @TypeConverters(StringListConverter::class)
    @ColumnInfo(name = "list_of_task")
    val listOfTasks: List<TaskModel?>?

)