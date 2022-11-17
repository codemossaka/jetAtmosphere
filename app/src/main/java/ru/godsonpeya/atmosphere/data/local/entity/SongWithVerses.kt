package ru.godsonpeya.atmosphere.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class SongWithVerses(
    @Embedded
    var song: Song = Song(),

    @Relation(parentColumn = "id", entityColumn = "songId",)
    var verses: MutableList<Verse> = mutableListOf(),

    @Relation(parentColumn = "songbookId", entityColumn = "id")
    var songBook: SongBook = SongBook(),
)
