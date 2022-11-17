package ru.godsonpeya.atmosphere.data.local.entity

import androidx.room.DatabaseView
import androidx.room.Embedded
import androidx.room.Relation

data class LanguageWithSongBook(
    @Embedded var language: Language = Language(),
    @Relation(parentColumn = "id", entityColumn = "languageId")
    var songbooks: List<SongBook> = mutableListOf(),
)

data class LanguageWithDownloadedSongBook(
    @Embedded var languageEntity: Language = Language(),
    @Relation(parentColumn = "id", entityColumn = "languageId", entity = DownloadedSongBook::class)
    var songbooks: List<DownloadedSongBook> = mutableListOf(),
)

@DatabaseView("SELECT * FROM songBook WHERE isDownLoaded = 1")
class DownloadedSongBook {
    @Embedded
    var songbooks: SongBook? = null
}
