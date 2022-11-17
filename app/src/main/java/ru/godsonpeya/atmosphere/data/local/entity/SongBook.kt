package ru.godsonpeya.atmosphere.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "songBook")
data class SongBook(
    @PrimaryKey
    var id: Int? = null,
    var name: String? = null,
    var code: String? = null,
    var description: String? = null,
    var isDownLoaded: Boolean = false,
    val languageId: Int? = null,
//            Date
)
