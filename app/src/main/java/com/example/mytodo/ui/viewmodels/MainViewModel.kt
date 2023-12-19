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

    fun addNewListDialogVisablity() {
        _listUiState.update { it.copy(showDialogAddNewList = !it.showDialogAddNewList) }
        resetAddNewListDialog()
    }

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
            addNewListDialogVisablity()
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

    fun addNewTaskDialogVisablity() { _taskUiState.update { it.copy(showDialogAddNewTask = !it.showDialogAddNewTask) } }

    fun createNewTask() {
        val newTasks: MutableList<TaskModel> = taskUiState.value.tasks.toMutableList()
        val newNumberOfTasks = taskUiState.value.numberOfTasks.inc()

        newTasks.add(TaskModel(newTaskName))

        viewModelScope.launch(Dispatchers.IO) {
            listDao.update(
                ListEntity(
                    id = taskUiState.value.listId,
                    listName = taskUiState.value.listName,
                    icon = taskUiState.value.listIcon,
                    colorTheme = taskUiState.value.colorTheme,
                    numberOfTasks = newNumberOfTasks,
                    listOfTasks = newTasks.toList()
                )
            )
            updateTasksInUiState(_taskUiState.value.listId)
        }

        updateNewTaskName("")
    }

    fun applyTaskScreenDetails(list: ListEntity) {
        _taskUiState.update { taskUiState ->
            taskUiState.copy(
                listId = list.id,
                listName = list.listName,
                listIcon = list.icon,
                numberOfTasks = list.numberOfTasks,
                colorTheme = list.colorTheme
            )
        }
        updateTasksInUiState(list.id)
    }

    private fun updateTasksInUiState(listId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val tasks = listDao.getListById(listId)
            _taskUiState.update { taskUiState ->
                taskUiState.copy(
                    tasks = tasks.listOfTasks ?: listOf(),
                    numberOfTasks = tasks.numberOfTasks
                )
            }
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
            listDao.update(
                ListEntity(
                    id = taskUiState.value.listId,
                    listName = taskUiState.value.listName,
                    icon = taskUiState.value.listIcon,
                    colorTheme = taskUiState.value.colorTheme,
                    numberOfTasks = taskUiState.value.numberOfTasks,
                    listOfTasks = taskUiState.value.tasks
                )
            )
        }
    }

}