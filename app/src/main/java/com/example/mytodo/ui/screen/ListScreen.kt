package com.example.mytodo.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mytodo.data.room.ListEntity
import com.example.mytodo.ui.element.AddNewListDialog
import com.example.mytodo.ui.element.ListItem
import com.example.mytodo.ui.element.ListTopBar
import com.example.mytodo.ui.element.ListBottomBar
import com.example.mytodo.ui.theme.MyToDoTheme

@Composable
fun ListScreen(
    lists: List<ListEntity>,
    onListItemClicked: (ListEntity) -> Unit,
    newListName: String,
    newListColorTheme: String,
    newListIcon: String,
    onNewListNameChange: (String) -> Unit,
    onNewListColorThemeChange: (String) -> Unit,
    onNewListIconClicked: (String) -> Unit,
    onCreateListClicked: () -> Unit,
) {
    var showDialogAddNewList by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            ListTopBar(
                name = "Omar Khaled",
                email = "omarabdelaal2013@gmail.com"
            )
        },
        bottomBar = {
            ListBottomBar(
                onAddNewListButtonClicked = { showDialogAddNewList = !showDialogAddNewList }
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
            newListIcon = newListIcon,
            onNewListNameChange = onNewListNameChange,
            onNewListColorThemeChange = onNewListColorThemeChange,
            onNewListIconClicked = onNewListIconClicked,
            onCreateListClicked = {
                onCreateListClicked()
                showDialogAddNewList = !showDialogAddNewList
            },
            onCreateListCanceled = {
                onNewListNameChange("")
                onNewListColorThemeChange("primary")
                onNewListIconClicked("")
                showDialogAddNewList = !showDialogAddNewList }
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
                numberOfTask = list.listOfTasks?.size ?: 0,
                colorTheme = list.colorTheme,
                onListItemClicked = { onListItemClicked(list) }
            )
        }
    }
}

@Preview(name = "Light Mode")
@Preview(name = " Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ListScreenPreview() {
    MyToDoTheme {
        ListScreen(
            lists = emptyList(),
            onListItemClicked = {},
            newListName = "",
            newListColorTheme = "primary",
            newListIcon = "add_icon_list",
            onNewListNameChange = { _ -> },
            onNewListColorThemeChange = { _ -> },
            onNewListIconClicked = {},
            onCreateListClicked = {},
        )
    }
}
