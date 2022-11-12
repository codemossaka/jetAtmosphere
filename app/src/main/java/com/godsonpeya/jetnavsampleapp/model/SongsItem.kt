package com.godsonpeya.jetnavsampleapp.model

data class SongsItem(
    val addedBy: String,
    val audio: Boolean,
    val audioUrl: String,
    val author: Any,
    val authorId: Any,
    val categories: List<Any>,
    val chord: Boolean,
    val chordUrl: Any,
    val code: String,
    val description: Any,
    val id: Int,
    val language: Language,
    val languageId: Int,
    val mainChord: String,
    val name: String,
    val number: String,
    val songBook: SongBook,
    val songbookId: Int,
    val verses: List<Verse>
)