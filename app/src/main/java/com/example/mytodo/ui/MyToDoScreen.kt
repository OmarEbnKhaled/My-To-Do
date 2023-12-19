package com.example.mytodo.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mytodo.ui.screen.ListScreen
import com.example.mytodo.ui.screen.TaskScreen
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
                    mainViewModel.applyTaskScreenDetails(list)
                    navController.navigate(MyScreens.Tasks.name)
                },
                newListName = mainViewModel.newListName,
                newListColorTheme = mainViewModel.newListColorTheme,
                newListIcon = mainViewModel.newListIcon,
                onNewListNameChange = { newName -> mainViewModel.updateNewListName(newName) },
                onNewListColorThemeChange = { newColorTheme -> mainViewModel.updateNewListColorTheme(newColorTheme) },
                onNewListIconClicked = { newIcon -> mainViewModel.updateNewListIcon(newIcon) },
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