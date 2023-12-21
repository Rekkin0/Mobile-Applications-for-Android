package com.example.labfiveapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DBDao {
    @Query("SELECT * FROM list_item WHERE id = :id")
    fun getById(id: Int): DBListItem?

    @Query("SELECT * FROM list_item ORDER BY id ASC")
    fun getAll(): List<DBListItem>

    @Query("SELECT * FROM list_item ORDER BY id ASC")
    fun getAllLive(): LiveData<List<DBListItem>>

    @Query("SELECT * FROM list_item ORDER BY id ASC")
    fun getAllFlow(): Flow<List<DBListItem>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(item: DBListItem) : Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFlow(item: DBListItem) : Long

    @Update
    fun update(item: DBListItem) : Int

    @Update
    suspend fun updateFlow(item: DBListItem) : Int

    @Delete
    fun delete(item: DBListItem) : Int

    @Delete
    suspend fun deleteFlow(item: DBListItem) : Int

    @Query("DELETE FROM list_item")
    fun deleteAll()
}