package com.example.mytodo.ui.uistate

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.example.mytodo.R
import com.example.mytodo.data.TASKS
import com.example.mytodo.models.TaskModel

data class TaskUiState(
    val tasks: MutableList<TaskModel> = TASKS,
    val showDialogAddNewTask: Boolean = false,
    val listName: String = "",
    val listIcon: String = "list",
    @ColorRes val colorTheme: Int = R.color.maroon
) {
    fun addNewTask(newTask: TaskModel): MutableList<TaskModel> {
        tasks.add(newTask)
        return tasks
    }
}