package com.example.mytodo.ui.uistate

import com.example.mytodo.models.TaskModel

data class TaskUiState(
    val tasks: List<TaskModel> = listOf(),
    val showDialogAddNewTask: Boolean = false,
    val listId: Int = 0,
    val listName: String = "",
    val listIcon: String = "list",
    val numberOfTasks: Int = 0,
    val colorTheme: String = "primary"
)