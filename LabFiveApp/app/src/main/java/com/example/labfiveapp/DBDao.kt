package com.example.labfiveapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DBDao {
    @Query("SELECT * FROM list_item ORDER BY id ASC")
    fun getAll(): MutableList<DBListItem>?

    @Query("DELETE FROM list_item")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(item: DBListItem?) : Long

    @Delete
    fun delete(item: DBListItem?) : Int
}