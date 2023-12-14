package com.example.labfiveapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.random.Random

@Entity(tableName = "list_item")
class DBListItem {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "name")
    var name: String = "New champion no.$id"

    @ColumnInfo(name = "title")
    var title: String = "New title"

    @ColumnInfo(name = "rating")
    var rating: Float = 0.0F

    @ColumnInfo(name = "region")
    var region: Int? = null

    constructor(name: String, title: String, rating: Float, region: Int?) {
        this.name = name
        this.title = title
        this.rating = rating
        this.region = region
    }

    constructor(num: Int) : this(
        "New champion no.$num",
        "New title",
        Random.nextInt(0, 11) / 2.0F,
        Random.nextInt(1, 4)
    )

    constructor() : this(
        "New champion",
        "New title",
        Random.nextInt(0, 11) / 2.0F,
        Random.nextInt(1, 4)
    )

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is DBListItem) {
            return false
        }
        return (
            this.id == other.id
            && this.name == other.name
            && this.title == other.title
            && this.rating == other.rating
            && this.region == other.region
        )
    }
}