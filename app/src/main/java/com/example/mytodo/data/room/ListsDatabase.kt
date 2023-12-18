package com.example.mytodo.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mytodo.data.converters.StringListConverter

@TypeConverters(StringListConverter::class)
@Database(entities = [ListEntity::class], version = 1, exportSchema = false)
abstract class ListsDatabase : RoomDatabase() {

    abstract fun listDao(): ListDao

    companion object {

        @Volatile
        private var Instance: ListsDatabase? = null

        fun getDatabase(context: Context): ListsDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    ListsDatabase::class.java,
                    "list_database"
                )
                    .fallbackToDestructiveMigration()
                    .addTypeConverter(StringListConverter())
                    .build()
                    .also { Instance = it }
            }
        }
    }

}