package com.example.labfiveapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface DBDao {
    @Query("SELECT * FROM list_item WHERE id = :id")
    fun getById(id: Int): DBListItem?

    @Query("SELECT * FROM list_item ORDER BY id ASC")
    fun getAll(): MutableList<DBListItem>?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(item: DBListItem) : Long

    @Update
    fun update(item: DBListItem) : Int

    @Delete
    fun delete(item: DBListItem) : Int

    @Query("DELETE FROM list_item")
    fun deleteAll()
}