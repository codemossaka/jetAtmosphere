package ru.godsonpeya.atmosphere.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

class SongBookWithLanguage {
    @Embedded
    var songbook: SongBook = SongBook()

    @Relation(parentColumn = "languageId", entityColumn = "id")
    var languageEntity: Language = Language()
}
