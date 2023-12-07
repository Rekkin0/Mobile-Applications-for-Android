package com.example.labfiveapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase

@Database(entities = [DBListItem::class], version = 1)
abstract class DBList : RoomDatabase() {
    abstract fun dbDao(): DBDao?

    companion object {
        private var DB_INSTANCE: DBList? = null

        @Synchronized
        fun getDatabase(context: Context): DBList? {
            if (DB_INSTANCE == null) {
                DB_INSTANCE = databaseBuilder(
                    context.applicationContext,
                    DBList::class.java,
                    "list_item_database"
                ).allowMainThreadQueries()
                .build()
            }
            return DB_INSTANCE
        }
    }

}