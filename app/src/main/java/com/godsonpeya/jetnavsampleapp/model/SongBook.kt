package com.godsonpeya.jetnavsampleapp.model

data class SongBook(
    val addedBy: String,
    val code: String,
    val createdAt: String,
    val description: Any,
    val hidden: Boolean,
    val id: Int,
    val isDraft: Boolean,
    val language: Language,
    val languageId: Int,
    val name: String,
    val updatedAt: String,
    val userId: Int,
    val version: String
)