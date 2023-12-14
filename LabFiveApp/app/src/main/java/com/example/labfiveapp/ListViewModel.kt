package com.example.labfiveapp

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras

class ListViewModel(applicationContext: Context) : ViewModel() {
    fun getAllItems(): MutableList<DBListItem> = dataRepository.getAll()!!
    fun getItemById(id: Int) = dataRepository.getById(id)!!
    fun insertItem(item: DBListItem) = dataRepository.insert(item)
    fun updateItem(item: DBListItem) = dataRepository.update(item)
    fun deleteItem(item: DBListItem) = dataRepository.delete(item)

    init {
        dataRepository = DataRepository(applicationContext)
    }

    companion object {
        private lateinit var dataRepository: DataRepository
        val Factory: ViewModelProvider.Factory = object: ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                return ListViewModel(application.applicationContext) as T
            }
        }
    }
}