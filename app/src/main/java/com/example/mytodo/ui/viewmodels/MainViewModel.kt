package com.example.mytodo.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytodo.data.room.ListDao
import com.example.mytodo.data.room.ListEntity
import com.example.mytodo.models.TaskModel
import com.example.mytodo.ui.uistate.ListUiState
import com.example.mytodo.ui.uistate.TaskUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(private val listDao: ListDao) : ViewModel() {

    init {
        updateListsInUiState()
    }

    /**
     * Lists UI */
    private val _listUiState = MutableStateFlow(ListUiState())
    val listUiState: StateFlow<ListUiState> = _listUiState.asStateFlow()

    var newListName by mutableStateOf("")
        private set
    fun updateNewListName(newName: String) { newListName = newName }

    var newListColorTheme by mutableStateOf("primary")
        private set
    fun updateNewListColorTheme(newColorTheme: String) { newListColorTheme = newColorTheme }

    var newListIcon by mutableStateOf("")
    fun updateNewListIcon(newIcon: String) { newListIcon = newIcon }

    fun createNewList() {
        viewModelScope.launch(Dispatchers.IO) {
            listDao.insert(
                listEntity = ListEntity(
                    listName = newListName,
                    icon = if (newListIcon == "") "list" else newListIcon,
                    colorTheme = newListColorTheme,
                    listOfTasks = null
                )
            )
            resetAddNewListDialog()
        }
    }

    fun deleteList() {
        viewModelScope.launch(Dispatchers.IO) {
            listDao.delete(
                ListEntity(
                    id = taskUiState.value.listId,
                    listName = taskUiState.value.listName,
                    icon = taskUiState.value.listIcon,
                    colorTheme = taskUiState.value.colorTheme,
                    listOfTasks = taskUiState.value.tasks,
                )
            )
        }
    }

    private fun updateListsInUiState() {
        viewModelScope.launch(Dispatchers.IO) {
            listDao.getAllLists().collect { lists ->
                _listUiState.update { listUiState ->
                    listUiState.copy(
                        lists = lists
                    )
                }
            }
        }
    }

    private fun resetAddNewListDialog() {
        updateNewListName("")
        updateNewListColorTheme("primary")
        updateNewListIcon("")
    }



    /**
     * Tasks UI */
    private val _taskUiState = MutableStateFlow(TaskUiState())
    val taskUiState: StateFlow<TaskUiState> = _taskUiState.asStateFlow()

    var newTaskName by mutableStateOf("")
        private set
    fun updateNewTaskName(newName: String) { newTaskName = newName }

    fun createNewTask() {
        val newTasks: MutableList<TaskModel> = taskUiState.value.tasks.toMutableList()
        newTasks.add(TaskModel(newTaskName))
        updateTasksInUiState(newTasks)
        viewModelScope.launch(Dispatchers.IO) {
            updateListInDatabase(
                tasks = newTasks.toList()
            )
        }
        updateNewTaskName("")
    }

    fun deleteTask(task: TaskModel) {
        val newTasks: MutableList<TaskModel> = taskUiState.value.tasks.toMutableList()
        newTasks.remove(task)
        updateTasksInUiState(newTasks)
        viewModelScope.launch(Dispatchers.IO) {
            updateListInDatabase(
                tasks = newTasks.toList()
            )
        }
    }

    private fun updateTasksInUiState(newTasks: MutableList<TaskModel>) {
        _taskUiState.update { taskUiState ->
            taskUiState.copy(
                tasks = newTasks.toList()
            )
        }
    }

    fun applyTaskScreenDetails(list: ListEntity) {
        _taskUiState.update { taskUiState ->
            taskUiState.copy(
                listId = list.id,
                listName = list.listName,
                listIcon = list.icon,
                numberOfTasks = list.listOfTasks?.size ?: 0,
                colorTheme = list.colorTheme,
                tasks = list.listOfTasks ?: listOf()
            )
        }
    }

    fun changeTaskIsFinished(item: TaskModel, finished: Boolean) =
        _taskUiState.value.tasks.find { it == item }?.let { task ->
            task.isFinished = finished
            changeTaskStatusInDatabase()
        }

    fun changeTaskIsImportant(item: TaskModel, important: Boolean) =
        _taskUiState.value.tasks.find { it == item }?.let { task ->
            task.isImportant = important
            changeTaskStatusInDatabase()
        }

    private fun changeTaskStatusInDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            updateListInDatabase()
        }
    }

    fun updateListColorThemeDirect(newColor: String) {
        _taskUiState.update { taskUiState ->
            taskUiState.copy(
                colorTheme = newColor
            )
        }
        viewModelScope.launch(Dispatchers.IO) {
            updateListInDatabase()
        }
    }

    fun updateListProperties() {
        _taskUiState.update { taskUiState ->
            taskUiState.copy(
                listName = newListName,
                listIcon = newListIcon,
                colorTheme = newListColorTheme,
            )
        }
        viewModelScope.launch(Dispatchers.IO) {
            updateListInDatabase()
            resetAddNewListDialog()
        }
    }

    private suspend fun updateListInDatabase(
        tasks: List<TaskModel> = taskUiState.value.tasks
    ) {
        listDao.update(
            ListEntity(
                id = taskUiState.value.listId,
                listName = taskUiState.value.listName,
                icon = taskUiState.value.listIcon,
                colorTheme = taskUiState.value.colorTheme,
                listOfTasks = tasks
            )
        )
    }

}