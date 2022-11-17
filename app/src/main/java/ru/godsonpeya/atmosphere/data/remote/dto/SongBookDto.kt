package ru.godsonpeya.atmosphere.data.remote.dto

data class SongBookDto(
    var addedBy: String,
    var code: String,
    var createdAt: String,
    var description: String,
    var hidden: Boolean,
    var id: Int,
    var isDraft: Boolean,
    var language: LanguageDto,
    var languageId: Int,
    var name: String,
    var updatedAt: String,
    var isDownLoaded: Boolean,
    var userId: Int,
    var version: String,
)