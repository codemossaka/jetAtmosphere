package ru.godsonpeya.atmosphere.data.remote.dto

data class SongsItem(
    val addedBy: String,
    val audio: Boolean,
    val audioUrl: String,
    val author: String,
    val authorId: Int,
    val categories: List<Any>,
    val chord: Boolean,
    val chordUrl: String,
    val code: String,
    val description: String,
    val id: Int,
    val language: LanguageDto,
    val languageId: Int,
    val mainChord: String,
    val name: String,
    val number: String,
    val songBook: SongBookDto,
    val songbookId: Int,
    val verses: List<VerseDto>,
)