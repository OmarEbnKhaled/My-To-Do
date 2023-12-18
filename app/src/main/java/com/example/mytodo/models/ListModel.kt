package com.example.mytodo.models

import androidx.annotation.ColorRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector

data class ListModel(
    var listIcon: ImageVector = Icons.Default.List,
    var listName: String,
    var numberOfTask: Int = 0,
    @ColorRes var colorTheme: Int
)