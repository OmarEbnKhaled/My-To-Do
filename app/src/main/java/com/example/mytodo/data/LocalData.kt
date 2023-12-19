package com.example.mytodo.data

import com.example.mytodo.R
import com.example.mytodo.data.room.ListEntity
import com.example.mytodo.models.TaskModel

val LISTS = listOf(
    ListEntity(
        listName = "My Day",
        icon = "sun",
        colorTheme = "gray",
        numberOfTasks = 0,
        listOfTasks = null
    ),
    ListEntity(
        listName = "My Day",
        icon = "sun",
        colorTheme = "teal",
        numberOfTasks = 0,
        listOfTasks = null
    ),
    ListEntity(
        listName = "Important",
        icon = "star",
        colorTheme = "maroon",
        numberOfTasks = 0,
        listOfTasks = null
    ),
    ListEntity(
        listName = "Planned",
        icon = "calender",
        colorTheme = "green",
        numberOfTasks = 0,
        listOfTasks = null
    ),
    ListEntity(
        listName = "Assigned to me",
        icon = "person",
        colorTheme = "olive",
        numberOfTasks = 0,
        listOfTasks = null
    ),
    ListEntity(
        listName = "Home",
        icon = "home",
        colorTheme = "primary",
        numberOfTasks = 0,
        listOfTasks = null
    ),
)


val TASKS by lazy {
    mutableListOf(
        TaskModel(
            "Task..1"
        ),
        TaskModel(
            "Task..2"
        ),
        TaskModel(
            "Task..3"
        ),
        TaskModel(
            "Task..4"
        ),
        TaskModel(
            "Task..5"
        ),
        TaskModel(
            "Task..6"
        )
    )
}

val COLORS = hashMapOf(
    "gray" to R.color.gray,
    "olive" to R.color.olive,
    "purple" to R.color.purple,
    "maroon" to R.color.maroon,
    "teal" to R.color.teal,
    "green" to R.color.green,
    "primary" to R.color.primary
)

val ICONS = hashMapOf(
    "list" to R.drawable.ic_list,
    "star" to R.drawable.ic_star,
    "home" to R.drawable.ic_home,
    "sun" to R.drawable.ic_sun,
    "calendar" to R.drawable.ic_calendar,
    "person" to R.drawable.ic_person,
)