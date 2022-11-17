package ru.godsonpeya.atmosphere.data.remote.dto

data class CustomSongBookItem(
    val code: String,
    val id: Int,
    val name: String,
    val songBooks: List<SongBookDto>,
)