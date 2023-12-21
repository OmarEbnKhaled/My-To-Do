package com.example.mytodo.ui

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
        navController = navController,
        startDestination = MyScreens.Lists.name,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        modifier = Modifier.fillMaxSize()
    ) {

        composable(
            route = MyScreens.Lists.name,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -it }, // it == fullWidth
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearEasing
                    )
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX  = { -it }, // it == fullWidth
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearEasing
                    )
                )
            }
        ) {
            ListScreen(
                lists = listUiState.lists,
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
                onCreateListClicked = { mainViewModel.createNewList() }
            )
        }

        composable(
            route = MyScreens.Tasks.name,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { it }, // it == fullWidth
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearEasing
                    )
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX  = { it }, // it == fullWidth
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearEasing
                    )
                )
            },
        ) {
            TaskScreen(
                tasks = taskUiState.tasks,
                listName = taskUiState.listName,
                listIcon = taskUiState.listIcon,
                listColorTheme = taskUiState.colorTheme,
                newTaskName = mainViewModel.newTaskName,
                onNewTaskNameChange = { mainViewModel.updateNewTaskName(it) },
                onBackupButtonClicked = { navController.navigateUp() },
                onCreateTaskClicked = { mainViewModel.createNewTask() },
                onIsFinishedChange = { task, finished -> mainViewModel.changeTaskIsFinished(task, finished) },
                onIsImportantChange = { task, important -> mainViewModel.changeTaskIsImportant(task, important) },
                onSwipeTaskToDismiss = { task -> mainViewModel.deleteTask(task) },
                onListColorThemeDirectChange = { newColorTheme -> mainViewModel.updateListColorThemeDirect(newColorTheme) },
                onDeleteListClicked = {
                    mainViewModel.deleteList()
                    navController.navigateUp()
                },
                newListName = mainViewModel.newListName,
                newListColorTheme = mainViewModel.newListColorTheme,
                newListIcon = mainViewModel.newListIcon,
                onListNameChange = { newName -> mainViewModel.updateNewListName(newName) },
                onListIconChange = { newIcon -> mainViewModel.updateNewListIcon(newIcon) },
                onListColorThemeChange = { newColorTheme -> mainViewModel.updateNewListColorTheme(newColorTheme) },
                onSaveListClicked = { mainViewModel.updateListProperties() }
            )
        }

    }
}