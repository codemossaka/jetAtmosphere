package ru.godsonpeya.atmosphere.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.ColumnInfo.TEXT
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "verse",
    foreignKeys = [
        ForeignKey(
            entity = Song::class,
            parentColumns = ["id"],
            childColumns = ["songId"],
            onDelete = ForeignKey.CASCADE
        )
    ])
data class Verse(
    @PrimaryKey
    var id: Int? = null,
    var verseOrder: Int? = null,
    var orderToShow: Int? = null,
    var isRefrain: Boolean = false,
    @ColumnInfo(name = "line", typeAffinity = TEXT)
    var line: String? = null,
    var songId: Int? = null,
)