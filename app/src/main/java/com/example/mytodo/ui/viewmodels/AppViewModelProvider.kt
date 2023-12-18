package com.example.mytodo.ui.viewmodels

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mytodo.MyToDoApplication

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            MainViewModel(
                myToDoApplication().database.listDao()
            )
        }
    }
}

fun CreationExtras.myToDoApplication(): MyToDoApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyToDoApplication)