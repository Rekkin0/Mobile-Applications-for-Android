package com.example.labfiveapp

import android.content.Context

class DataRepository(context: Context) {
    //private var itemList: MutableList<DBListItem>? = null
    private var dbDao: DBDao
    private var db: DB

    companion object {
        private var REPOSITORY_INSTANCE: DataRepository? = null

        fun getRepository(context: Context): DataRepository {
            if (REPOSITORY_INSTANCE == null) {
                REPOSITORY_INSTANCE = DataRepository(context)
            }
            return REPOSITORY_INSTANCE as DataRepository
        }
    }

    init {
        db = DB.getDatabase(context)!!
        dbDao = db.dbDao()!!
    }

    fun getAll(): MutableList<DBListItem>? {
        return dbDao.getAll()
    }

    fun insert(item: DBListItem?): Boolean {
        return dbDao.insert(item) >= 0
    }

    fun delete(item: DBListItem?): Boolean {
        return dbDao.delete(item) > 0
    }
}