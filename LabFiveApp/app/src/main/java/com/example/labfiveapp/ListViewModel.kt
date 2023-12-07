package com.example.labfiveapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class ListItem(
    var name: String = "",
    var title: String = "",
    var rating: Float = 0.0F,
    var region: Int? = null,
)

class ListViewModel : ViewModel() {
    val itemList = MutableLiveData<MutableList<ListItem>>()
    var item: ListItem

    init {
        val items: MutableList<ListItem> = mutableListOf(
            ListItem("Garen", "The Might of Demacia", 1.5F, 2),
            ListItem("Darius", "The Hand of Noxus", 2.5F, 1),
            ListItem("Fiora", "The Grand Duelist", 3.5F, 2),
            ListItem("Azir", "The Emperor of the Sands", 5F, 3),
            ListItem("Taliyah", "The Stoneweaver", 4.5F, 3),
            ListItem("Swain", "The Noxian Grand General", 4F, 1),
        )
        itemList.value = items
        item = items[0]
    }

    fun addItem(item: ListItem) {
        itemList.value!!.add(item)
    }

    fun removeItem(item: ListItem) {
        itemList.value!!.remove(item)
    }
}