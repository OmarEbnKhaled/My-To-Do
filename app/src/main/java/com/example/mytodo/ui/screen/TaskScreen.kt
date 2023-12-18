package com.example.mytodo.ui.screen

import androidx.annotation.ColorRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mytodo.R
import com.example.mytodo.data.TASKS
import com.example.mytodo.models.TaskModel
import com.example.mytodo.ui.element.AddNewTaskBottomSheet
import com.example.mytodo.ui.element.TaskItem
import com.example.mytodo.ui.element.TaskTopBar
import com.example.mytodo.ui.theme.MyToDoTheme

@Composable
fun TaskScreen(
    tasks: List<TaskModel>,
    showDialogAddNewTask: Boolean,
    listName: String,
    listIcon: String,
    @ColorRes colorTheme: Int,
    newTaskName: String,
    onNewTaskNameChange: (String) -> Unit,
    onBackupButtonClicked: () -> Unit,
    onFloatingActionButtonClicked: () -> Unit,
    onCreateTaskClicked: () -> Unit,
    onCancel: () -> Unit,
    onIsFinishedChange: (TaskModel, Boolean) -> Unit,
    onIsImportantChange: (TaskModel, Boolean) -> Unit
) {
    Scaffold(
        topBar = {
            TaskTopBar(
                listName = listName,
                listIcon = listIcon,
                colorTheme = colorTheme,
                onBackupButtonClicked = { onBackupButtonClicked() }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onFloatingActionButtonClicked,
                containerColor = colorResource(id = colorTheme),
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Task",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }
        },
        content = { padding ->
            LazyTasks(
                tasks = tasks,
                onIsFinishedChange = { task, finished ->
                    onIsFinishedChange(task, finished)
                },
                onIsImportantChange = { task, important ->
                    onIsImportantChange(task, important)
                },
                modifier = Modifier.padding(top = padding.calculateTopPadding())
            )
        }
    )

    if (showDialogAddNewTask) {
        AddNewTaskBottomSheet(
            newTaskName = newTaskName,
            colorTheme = colorTheme,
            onNewTaskNameChange = { onNewTaskNameChange(it) },
            onCreateTaskClicked = onCreateTaskClicked,
            onCancel = onCancel
        )
    }
}

@Composable
fun LazyTasks(
    tasks: List<TaskModel>,
    onIsFinishedChange: (TaskModel, Boolean) -> Unit,
    onIsImportantChange: (TaskModel, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp),
        modifier = modifier
    ){
        items(tasks) { task ->
            TaskItem(
                modifier = Modifier.padding(4.dp),
                title = task.title,
                colorTheme = task.colorTheme,
                isFinished = task.isFinished,
                onIsFinishedChange = { finished -> onIsFinishedChange(task, finished) },
                isImportant = task.isImportant,
                onIsImportantChange = { important -> onIsImportantChange(task, important) }
            )
        }
    }
}

@Preview
@Composable
fun TaskScreenPreview() {
    MyToDoTheme {
        TaskScreen(
            tasks = TASKS,
            showDialogAddNewTask= false,
            listName = "Home",
            listIcon = "list",
            colorTheme = R.color.primary,
            newTaskName = "",
            onNewTaskNameChange = {},
            onBackupButtonClicked = {},
            onFloatingActionButtonClicked = {},
            onCreateTaskClicked = {},
            onCancel = {},
            onIsFinishedChange = { _, _ -> },
            onIsImportantChange = { _, _ -> },
        )
    }
}
