package ru.godsonpeya.atmosphere.data.remote.dto

data class VerseDto(
    val createdAt: String,
    val id: Int,
    val isRefrain: Boolean,
    val line: String,
    val orderToShow: Int,
    val songId: Int,
    val updatedAt: String,
    val verseOrder: Int,
)