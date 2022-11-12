package com.godsonpeya.jetnavsampleapp.model

data class Verse(
    val createdAt: String,
    val id: Int,
    val isRefrain: Boolean,
    val line: String,
    val orderToShow: Any,
    val songId: Int,
    val updatedAt: String,
    val verseOrder: Int
)