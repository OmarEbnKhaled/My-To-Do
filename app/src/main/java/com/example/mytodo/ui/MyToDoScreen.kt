package com.example.mytodo.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mytodo.data.ICONS
import com.example.mytodo.ui.screen.ListScreen
import com.example.mytodo.ui.screen.TaskScreen
import com.example.mytodo.ui.theme.MyToDoTheme
import com.example.mytodo.ui.viewmodels.AppViewModelProvider
import com.example.mytodo.ui.viewmodels.MainViewModel

enum class MyScreens {
    Lists,
    Tasks
}

@Composable
fun MyToDoScreen(
    mainViewModel: MainViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navController: NavHostController = rememberNavController()
) {
    val listUiState by mainViewModel.listUiState.collectAsState()
    val taskUiState by mainViewModel.taskUiState.collectAsState()

    NavHost(
        navController =navController,
        startDestination = MyScreens.Lists.name,
        modifier = Modifier.fillMaxSize()
    ) {

        composable(route = MyScreens.Lists.name) {
            ListScreen(
                lists = listUiState.lists,
                onAddNewListButtonClicked = { mainViewModel.addNewListDialogVisablity() },
                showDialogAddNewList = listUiState.showDialogAddNewList,
                onListItemClicked = { list ->
                    mainViewModel.applyListDetails(
                        listName = list.listName,
                        listIcon = list.icon,
                        numberOfTask = list.numberOfTasks,
                        colorTheme = list.colorTheme
                    )
                    navController.navigate(MyScreens.Tasks.name)
                },
                newListName = mainViewModel.newListName,
                newListColorTheme = mainViewModel.newListColorTheme,
                onNewListNameChange = { mainViewModel.updateNewListName(it) },
                onNewListColorThemeChange = { mainViewModel.updateNewListColorTheme(it) },
                onCreateListClicked = { mainViewModel.createNewList() },
                onCreateListCanceled = { mainViewModel.addNewListDialogVisablity() }
            )
        }

        composable(route = MyScreens.Tasks.name) {
            TaskScreen(
                tasks = taskUiState.tasks,
                showDialogAddNewTask = taskUiState.showDialogAddNewTask,
                listName = taskUiState.listName,
                listIcon = taskUiState.listIcon,
                colorTheme = taskUiState.colorTheme,
                newTaskName = mainViewModel.newTaskName,
                onNewTaskNameChange = { mainViewModel.updateNewTaskName(it) },
                onBackupButtonClicked = { navController.navigateUp() },
                onFloatingActionButtonClicked = { mainViewModel.addNewTaskDialogVisablity() },
                onCreateTaskClicked = { mainViewModel.createNewTask() },
                onCancel = { mainViewModel.addNewTaskDialogVisablity() },
                onIsFinishedChange = { task, finished ->
                    mainViewModel.changeTaskIsFinished(task, finished)
                },
                onIsImportantChange = { task, important ->
                    mainViewModel.changeTaskIsImportant(task, important)
                }
            )
        }

    }
}

@Preview(name = "Light Mode")
@Preview(name = " Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MyToDoScreenPreview() {
    MyToDoTheme {
        MyToDoScreen()
    }
}