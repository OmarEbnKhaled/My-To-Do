package com.example.mytodo.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class TaskModel(
    val title: String = "",
    var isFinished: Boolean = false,
    var isImportant: Boolean = false
) /*{
    var isFinished: Boolean by mutableStateOf(false)
    var isImportant: Boolean by mutableStateOf(false)
}*/