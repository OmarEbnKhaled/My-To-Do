package com.example.mytodo.ui.uistate

import com.example.mytodo.data.room.ListEntity

data class ListUiState(
    val lists: List<ListEntity> = listOf(),
    val showDialogAddNewList: Boolean = false,
)