package ru.godsonpeya.atmosphere.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "song")
data class Song(
    @PrimaryKey
    var id: Int? = null,
    var number: String? = null,
    var code: String? = null,
    var chordUrl: String? = null,
    var name: String? = null,
    var description: String? = null,
    var mainChord: String? = null,
    var audioUrl: String? = null,
    var songbookId: Int? = null,
    var languageId: Int? = null,
    var authorId: Int? = null,
    var chord: Boolean? = null,
    var audio: Boolean? = null,
)