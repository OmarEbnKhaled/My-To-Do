package com.example.mytodo.ui.screen

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mytodo.R
import com.example.mytodo.data.COLORS
import com.example.mytodo.data.TASKS
import com.example.mytodo.models.TaskModel
import com.example.mytodo.ui.element.AddNewTaskBottomSheet
import com.example.mytodo.ui.element.ChangeThemeBottomSheet
import com.example.mytodo.ui.element.TaskItem
import com.example.mytodo.ui.element.TaskTopBar
import com.example.mytodo.ui.theme.MyToDoTheme

@Composable
fun TaskScreen(
    tasks: List<TaskModel>,
    listName: String,
    listIcon: String,
    colorTheme: String,
    newTaskName: String,
    onNewTaskNameChange: (String) -> Unit,
    onBackupButtonClicked: () -> Unit,
    onCreateTaskClicked: () -> Unit,
    onIsFinishedChange: (TaskModel, Boolean) -> Unit,
    onIsImportantChange: (TaskModel, Boolean) -> Unit,
    onSwipeTaskToDismiss: (TaskModel) -> Unit,
    onListColorThemeChange: (String) -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    var showBottomSheetChangeTheme by rememberSaveable { mutableStateOf(false) }
    var showBottomSheetAddNewTask by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .padding(top = 50.dp)
    ){
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = !expanded },
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
        ) {
            DropdownMenuItem(
                text = { Text(text = "Change theme") },
                onClick = {
                    showBottomSheetChangeTheme = !showBottomSheetChangeTheme
                    expanded = !expanded
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_theme),
                        contentDescription = null,
                    )
                }
            )
        }
    }
    Scaffold(
        topBar = {
            TaskTopBar(
                listName = listName,
                listIcon = listIcon,
                colorTheme = colorTheme,
                onBackupButtonClicked = { onBackupButtonClicked() },
                onMenuButtonClicked = { expanded = !expanded }
            )
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = !showBottomSheetAddNewTask,
                enter = scaleIn(),
                exit = scaleOut()
            ) {
                FloatingActionButton(
                    onClick = { showBottomSheetAddNewTask = !showBottomSheetAddNewTask },
                    containerColor = colorResource(id = COLORS[colorTheme]!!)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Task",
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        },
        content = { padding ->
            LazyTasks(
                tasks = tasks,
                colorTheme = colorTheme,
                onIsFinishedChange = { task, finished -> onIsFinishedChange(task, finished) },
                onIsImportantChange = { task, important -> onIsImportantChange(task, important) },
                onSwipeTaskToDismiss = { task -> onSwipeTaskToDismiss(task) },
                modifier = Modifier.padding(top = padding.calculateTopPadding())
            )
        }
    )

    if (showBottomSheetAddNewTask) {
        AddNewTaskBottomSheet(
            newTaskName = newTaskName,
            colorTheme = colorTheme,
            onNewTaskNameChange = { onNewTaskNameChange(it) },
            onCreateTaskClicked = { onCreateTaskClicked() },
            onCancel = { showBottomSheetAddNewTask = !showBottomSheetAddNewTask }
        )
    }

    if (showBottomSheetChangeTheme) {
        ChangeThemeBottomSheet(
            onListColorThemeChange = { onListColorThemeChange(it) },
            onDismiss = { showBottomSheetChangeTheme = !showBottomSheetChangeTheme }
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LazyTasks(
    tasks: List<TaskModel>,
    colorTheme: String,
    onIsFinishedChange: (TaskModel, Boolean) -> Unit,
    onIsImportantChange: (TaskModel, Boolean) -> Unit,
    onSwipeTaskToDismiss: (TaskModel) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp),
        modifier = modifier
    ){
        itemsIndexed(items = tasks, key = { _, tasks -> tasks.hashCode()}) { _, task ->

            var isFinished: Boolean by remember { mutableStateOf(task.isFinished) }
            var isImportant: Boolean by remember { mutableStateOf(task.isImportant) }

            val dismissState = rememberDismissState(
                confirmValueChange = {
                    if (it == DismissValue.DismissedToEnd) {
                        onSwipeTaskToDismiss(task)
                    }
                    true
                }
            )
            SwipeToDismiss(
                state = dismissState,
                background = {
                    val color = when(dismissState.dismissDirection) {
                        DismissDirection.EndToStart -> Color.Transparent
                        DismissDirection.StartToEnd -> MaterialTheme.colorScheme.surfaceVariant
                        null -> MaterialTheme.colorScheme.background
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color)
                            .padding(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color.Red,
                            modifier = Modifier.align(Alignment.CenterStart).size(35.dp)
                        )
                    }
                },
                dismissContent = {
                    TaskItem(
                        modifier = Modifier.padding(4.dp),
                        title = task.title,
                        colorTheme = colorTheme,
                        isFinished = isFinished,
                        onIsFinishedChange = {
                            finished -> onIsFinishedChange(task, finished)
                            isFinished = !isFinished
                        },
                        isImportant = isImportant,
                        onIsImportantChange = {
                            important -> onIsImportantChange(task, important)
                            isImportant = !isImportant
                        }
                    )
                },
                directions = setOf(DismissDirection.StartToEnd)
            )
            Divider()
        }
    }
}

@Preview(name = "Light Mode")
@Preview(name = " Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TaskScreenPreview() {
    MyToDoTheme {
        TaskScreen(
            tasks = TASKS,
            listName = "Home",
            listIcon = "list",
            colorTheme = "primary",
            newTaskName = "",
            onNewTaskNameChange = {},
            onBackupButtonClicked = {},
            onCreateTaskClicked = {},
            onIsFinishedChange = { _, _ -> },
            onIsImportantChange = { _, _ -> },
            onSwipeTaskToDismiss = { _ -> },
            onListColorThemeChange = { _ -> }
        )
    }
}
