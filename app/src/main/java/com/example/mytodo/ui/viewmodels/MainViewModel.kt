package com.example.mytodo.ui.viewmodels

import androidx.annotation.ColorRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytodo.R
import com.example.mytodo.data.room.ListDao
import com.example.mytodo.data.room.ListEntity
import com.example.mytodo.models.TaskModel
import com.example.mytodo.ui.uistate.ListUiState
import com.example.mytodo.ui.uistate.TaskUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(private val listDao: ListDao) : ViewModel() {

    init {
        viewModelScope.launch {
            updateListUiState()
        }
    }

    /**
     * Lists UI */
    private val _listUiState = MutableStateFlow(ListUiState())
    val listUiState: StateFlow<ListUiState> = _listUiState.asStateFlow()

    var newListName by mutableStateOf("")
        private set
    fun updateNewListName(newName: String) {
        newListName = newName
    }

    var newListColorTheme by mutableIntStateOf(R.color.primary)
        private set
    fun updateNewListColorTheme(newColorTheme: Int) {
        newListColorTheme = newColorTheme
    }

    fun addNewListDialogVisablity() {
        _listUiState.update { it.copy(showDialogAddNewList = !it.showDialogAddNewList) }
    }

    fun createNewList() {
        viewModelScope.launch {
            listDao.insert(
                listEntity = ListEntity(
                    listName = newListName,
                    colorTheme = newListColorTheme,
                    icon = "list",
                    listOfTasks = null
                )
            )
            updateListUiState()
        }
    }

    private suspend fun updateListUiState() {
        listDao.getAllLists().collect { lists ->
            _listUiState.update { listUiState ->
                listUiState.copy(
                    lists = lists,
                    showDialogAddNewList = false
                )
            }
        }
    }


    /**
     * Tasks UI */
    private val _taskUiState = MutableStateFlow(TaskUiState())
    val taskUiState: StateFlow<TaskUiState> = _taskUiState.asStateFlow()

    var newTaskName by mutableStateOf("")
        private set
    fun updateNewTaskName(newName: String) {
        newTaskName = newName
    }

    fun addNewTaskDialogVisablity() {
        _taskUiState.update { it.copy(showDialogAddNewTask = !it.showDialogAddNewTask) }
    }

    fun createNewTask() {
        _taskUiState.update {
            it.copy(
                tasks = it.addNewTask(
                    TaskModel(
                        title = newTaskName,
                        colorTheme = it.colorTheme
                    )
                ),
                /*showDialogAddNewTask = false,*/
            )
        }
        updateNewTaskName("")
    }

    fun applyListDetails(listName: String, listIcon: String, numberOfTask: Int, colorTheme: Int) {
        _taskUiState.update {
            it.copy(
                listName = listName,
                listIcon = listIcon,
                tasks = createTasks(numberOfTask, colorTheme),
                colorTheme = colorTheme
            )
        }
    }

    fun changeTaskIsFinished(item: TaskModel, finished: Boolean) =
        _taskUiState.value.tasks.find { it == item }?.let { task ->
            task.isFinished = finished
        }

    fun changeTaskIsImportant(item: TaskModel, important: Boolean) =
        _taskUiState.value.tasks.find { it == item }?.let { task ->
            task.isImportant = important
        }



    /**
     * Business Logic*/
    private fun createTasks(numberOfTask: Int, @ColorRes colorTheme: Int): MutableList<TaskModel> {
        return MutableList(numberOfTask) { TaskModel(title = "Task..$it", colorTheme = colorTheme) }
    }

}