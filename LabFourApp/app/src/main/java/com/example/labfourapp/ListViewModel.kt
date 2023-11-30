package com.example.labfourapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class ListItem(val title: String, val description: String)

class ListViewModel : ViewModel() {
    val listItems = MutableLiveData<List<ListItem>>()

    init {
        val items = listOf(
            ListItem("Android", "Mobile OS"),
            ListItem("Kotlin", "Programming Language")
        )
        listItems.value = items
    }
}