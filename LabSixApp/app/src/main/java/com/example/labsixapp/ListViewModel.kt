package com.example.labsixapp

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ListViewModel(applicationContext: Context) : ViewModel() {
    fun getItemById(id: Int) = dataRepository.getById(id)!!
    fun getAllItems(): List<DBListItem> = dataRepository.getAll()
    fun getAllItemsLive(): LiveData<List<DBListItem>> = dataRepository.getAllLive()
    fun getAllItemsFlow(): Flow<List<DBListItem>> = dataRepository.getAllFlow()
    fun insertItem(item: DBListItem) = dataRepository.insert(item)
    fun updateItem(item: DBListItem) = dataRepository.update(item)
    fun deleteItem(item: DBListItem) = dataRepository.delete(item)
    fun insertItemFlow(item: DBListItem) = viewModelScope.launch {
        dataRepository.insertFlow(item)
    }
    fun updateItemFlow(item: DBListItem) = viewModelScope.launch {
        dataRepository.updateFlow(item)
    }
    fun deleteItemFlow(item: DBListItem) = viewModelScope.launch {
        dataRepository.deleteFlow(item)
    }

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