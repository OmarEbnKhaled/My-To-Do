package com.example.mytodo.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ListDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(listEntity: ListEntity)

    @Update
    suspend fun update(listEntity: ListEntity)

    @Delete
    suspend fun delete(listEntity: ListEntity)

    @Query("SELECT * FROM lists ORDER BY id ASC")
    fun getAllLists(): Flow<List<ListEntity>>

    @Query("SELECT * FROM lists WHERE id = :id")
    fun getListById(id: Int): ListEntity

}