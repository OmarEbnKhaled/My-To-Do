package com.example.mytodo.ui.uistate

import androidx.compose.runtime.mutableStateListOf
import com.example.mytodo.models.TaskModel

data class TaskUiState(
    var tasks: List<TaskModel> = mutableStateListOf(),
    val listId: Int = 0,
    val listName: String = "",
    val listIcon: String = "list",
    val numberOfTasks: Int = 0,
    val colorTheme: String = "primary"
)