package ru.godsonpeya.atmosphere.extentions

import ru.godsonpeya.atmosphere.data.local.entity.Language
import ru.godsonpeya.atmosphere.data.local.entity.Song
import ru.godsonpeya.atmosphere.data.local.entity.SongBook
import ru.godsonpeya.atmosphere.data.local.entity.Verse
import ru.godsonpeya.atmosphere.data.remote.dto.CustomSongBooks
import ru.godsonpeya.atmosphere.data.remote.dto.SongBookDto
import ru.godsonpeya.atmosphere.data.remote.dto.Songs
import ru.godsonpeya.atmosphere.data.remote.dto.SongsItem


fun CustomSongBooks.asLanguageDatabaseModel2(): MutableList<Language> {
    return this.map { Language(id = it.id, name = it.name, code = it.code) }.toMutableList()
}

fun CustomSongBooks.asSongbookDatabaseModel2(): MutableList<SongBook> {
    val songbooksList: MutableList<SongBook> = mutableListOf()
    this.forEach { lang ->
        songbooksList.addAll(lang.songBooks.map {
            SongBook(id = it.id,
                name = it.name,
                code = it.code,
                isDownLoaded = it.isDownLoaded,
                description = it.description,
                languageId = it.languageId)
        })
    }
    return songbooksList
}

fun SongBookDto.asSongbookDatabaseModel2()= SongBook(id = this.id,
        name = this.name,
        code = this.code,
        isDownLoaded = this.isDownLoaded,
        description = this.description,
        languageId = this.languageId)


fun Songs.asSongsDatabaseModel2(): MutableList<Song> {
    return this.map {
        Song(id = it.id,
            number = it.number,
            code = it.code,
            chordUrl = it.chordUrl,
            name = it.name,
            description = it.description,
            mainChord = it.mainChord,
            audioUrl = it.audioUrl,
            songbookId = it.songbookId,
            languageId = it.languageId,
            authorId = it.authorId,
            chord = it.chord,
            audio = it.audio)
    }.toMutableList()
}

fun List<SongsItem>.asVersesDatabaseModel2(): MutableList<Verse> {
    val versesList: MutableList<Verse> = mutableListOf()
    this.forEach { lang ->
        lang.verses.let { it ->
            versesList.addAll(it.map {
                Verse(id = it.id,
                    verseOrder = it.verseOrder,
                    orderToShow = it.orderToShow,
                    isRefrain = it.isRefrain,
                    line = it.line,
                    songId = it.songId)
            })
        }
    }
    return versesList.toMutableList()
}
