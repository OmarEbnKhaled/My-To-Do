package com.example.mytodo

import android.app.Application
import com.example.mytodo.data.room.ListsDatabase

class MyToDoApplication : Application() {
    val database: ListsDatabase by lazy { ListsDatabase.getDatabase(this) }
}
