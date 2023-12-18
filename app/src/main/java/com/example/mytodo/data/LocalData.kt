package com.example.mytodo.data

import com.example.mytodo.R
import com.example.mytodo.data.room.ListEntity
import com.example.mytodo.models.TaskModel

val LISTS = listOf(
    ListEntity(
        listName = "My Day",
        icon = "sun",
        colorTheme = R.color.gray,
        numberOfTasks = 0,
        listOfTasks = null
    ),
    ListEntity(
        listName = "My Day",
        icon = "sun",
        colorTheme = R.color.teal,
        numberOfTasks = 0,
        listOfTasks = null
    ),
    ListEntity(
        listName = "Important",
        icon = "star",
        colorTheme = R.color.maroon,
        numberOfTasks = 0,
        listOfTasks = null
    ),
    ListEntity(
        listName = "Planned",
        icon = "calender",
        colorTheme = R.color.green,
        numberOfTasks = 0,
        listOfTasks = null
    ),
    ListEntity(
        listName = "Assigned to me",
        icon = "person",
        colorTheme = R.color.olive,
        numberOfTasks = 0,
        listOfTasks = null
    ),
    ListEntity(
        listName = "Home",
        icon = "home",
        colorTheme = R.color.primary,
        numberOfTasks = 0,
        listOfTasks = null
    ),
)


val TASKS by lazy {
    mutableListOf(
        TaskModel(
            "Task..1",
            COLORS["maroon"]!!
        ),
        TaskModel(
            "Task..2",
            COLORS["maroon"]!!
        ),
        TaskModel(
            "Task..3",
            COLORS["maroon"]!!
        ),
        TaskModel(
            "Task..4",
            COLORS["maroon"]!!
        ),
        TaskModel(
            "Task..5",
            COLORS["maroon"]!!
        ),
        TaskModel(
            "Task..6",
            COLORS["maroon"]!!
        )
    )
}

val COLORS = hashMapOf(
    "fuchsia" to R.color.fuchsia,
    "red" to R.color.red,
    "silver" to R.color.silver,
    "gray" to R.color.gray,
    "olive" to R.color.olive,
    "purple" to R.color.purple,
    "maroon" to R.color.maroon,
    "aqua" to R.color.aqua,
    "teal" to R.color.teal,
    "green" to R.color.green,
    "blue" to R.color.blue,
    "navy" to R.color.navy,
    "primary" to R.color.primary
)

val ICONS = hashMapOf(
    "list" to R.drawable.ic_list,
    "star" to R.drawable.ic_star,
    "home" to R.drawable.ic_home,
    "sun" to R.drawable.ic_sun,
    "calendar" to R.drawable.ic_calendar,
    "person" to R.drawable.ic_person
)