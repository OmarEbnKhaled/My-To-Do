package com.example.mytodo.ui.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mytodo.R
import com.example.mytodo.data.room.ListEntity
import com.example.mytodo.ui.element.AddNewListDialog
import com.example.mytodo.ui.element.ListItem
import com.example.mytodo.ui.element.ListTopBar
import com.example.mytodo.ui.element.ListBottomBar
import com.example.mytodo.ui.theme.MyToDoTheme

@Composable
fun ListScreen(
    lists: List<ListEntity>,
    showDialogAddNewList: Boolean,
    onAddNewListButtonClicked: () -> Unit,
    onListItemClicked: (ListEntity) -> Unit,
    newListName: String,
    newListColorTheme: Int,
    onNewListNameChange: (String) -> Unit,
    onNewListColorThemeChange: (Int) -> Unit,
    onCreateListClicked: () -> Unit,
    onCreateListCanceled: () -> Unit
) {
    Scaffold(
        topBar = {
            ListTopBar(
                name = "Omar Khaled",
                email = "omarabdelaal2013@gmail.com"
            )
        },
        bottomBar = {
            ListBottomBar(
                onAddNewListButtonClicked = onAddNewListButtonClicked
            )
        },
        content = { padding ->
            LazyTasksList(
                lists = lists,
                onListItemClicked = { list -> onListItemClicked(list) },
                modifier = Modifier.padding(
                        top = padding.calculateTopPadding(),
                        bottom = padding.calculateBottomPadding()
                )
            )
        }
    )

    if (showDialogAddNewList) {
        AddNewListDialog(
            newListName = newListName,
            newListColorTheme = newListColorTheme,
            onNewListNameChange = onNewListNameChange,
            onNewListColorThemeChange = onNewListColorThemeChange,
            onCreateListCanceled = onCreateListCanceled,
            onCreateListClicked = onCreateListClicked
        )
    }
}

@Composable
fun LazyTasksList(
    lists: List<ListEntity>,
    onListItemClicked: (ListEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(4.dp),
        modifier = modifier
    ) {
        items(lists) { list ->
            ListItem(
                listIcon = list.icon,
                listName = list.listName,
                numberOfTask = list.numberOfTasks,
                colorTheme = list.colorTheme,
                onListItemClicked = { onListItemClicked(list) }
            )
        }
    }
}

@Preview
@Composable
fun ListScreenPreview() {
    MyToDoTheme {
        ListScreen(
            lists = emptyList(),
            showDialogAddNewList = false,
            onAddNewListButtonClicked = {},
            onListItemClicked = {},
            newListName = "",
            newListColorTheme = R.color.maroon,
            onNewListNameChange = { _ -> },
            onNewListColorThemeChange = { _ -> },
            onCreateListClicked = {},
            onCreateListCanceled = {}
        )
    }
}
