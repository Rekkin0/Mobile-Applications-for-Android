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
        /*insert(DBListItem("Garen", "The Might of Demacia", 1.5F, 2))
        insert(DBListItem("Darius", "The Hand of Noxus", 2.5F, 1))
        insert(DBListItem("Fiora", "The Grand Duelist", 3.5F, 2))
        insert(DBListItem("Azir", "The Emperor of the Sands", 5F, 3))
        insert(DBListItem("Taliyah", "The Stoneweaver", 4.5F, 3))
        insert(DBListItem("Swain", "The Noxian Grand General", 4F, 1))*/
    }

    fun getById(id: Int): DBListItem? {
        return dbDao.getById(id)
    }

    fun getAll(): MutableList<DBListItem>? {
        return dbDao.getAll()
    }

    fun insert(item: DBListItem): Boolean {
        return dbDao.insert(item) >= 0
    }

    fun update(item: DBListItem): Boolean {
        return dbDao.update(item) > 0
    }

    fun delete(item: DBListItem): Boolean {
        return dbDao.delete(item) > 0
    }

    fun deleteAll() {
        dbDao.deleteAll()
    }
}