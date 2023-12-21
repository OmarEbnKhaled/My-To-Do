package com.example.mytodo.ui.uistate

import com.example.mytodo.models.TaskModel

data class TaskUiState(
    var tasks: List<TaskModel> = listOf(),
    val listId: Int = 0,
    val listName: String = "",
    val listIcon: String = "list",
    val numberOfTasks: Int = 0,
    val colorTheme: String = "primary"
)