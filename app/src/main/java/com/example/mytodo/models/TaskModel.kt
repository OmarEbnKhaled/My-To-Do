package com.example.mytodo.models

import androidx.annotation.ColorRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class TaskModel(
    val title: String = "",
    @ColorRes val colorTheme: Int
) {
    var isFinished: Boolean by mutableStateOf(false)
    var isImportant: Boolean by mutableStateOf(false)
}