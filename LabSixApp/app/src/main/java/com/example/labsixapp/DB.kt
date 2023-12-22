package com.example.labsixapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase

@Database(entities = [DBListItem::class], version = 1)
abstract class DB : RoomDatabase() {
    abstract fun dbDao(): DBDao?

    companion object {
        private var DB_INSTANCE: DB? = null

        @Synchronized
        fun getDatabase(context: Context): DB? {
            if (DB_INSTANCE == null) {
                DB_INSTANCE = databaseBuilder(
                    context.applicationContext,
                    DB::class.java,
                    "list_item_database"
                ).allowMainThreadQueries()
                .build()
            }
            return DB_INSTANCE
        }
    }

}