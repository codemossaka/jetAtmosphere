package ru.godsonpeya.atmosphere.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.godsonpeya.atmosphere.data.local.dao.LanguageDao
import ru.godsonpeya.atmosphere.data.local.dao.SongBookDao
import ru.godsonpeya.atmosphere.data.local.dao.SongDao
import ru.godsonpeya.atmosphere.data.local.dao.VerseDao
import ru.godsonpeya.atmosphere.data.local.entity.*
import ru.godsonpeya.atmosphere.utils.DateConverter
import ru.godsonpeya.atmosphere.utils.UUIDConverter

@Database(
    entities = [Language::class, SongBook::class, Song::class, Verse::class],
    views = [DownloadedSongBook::class],
    version = 5,
    exportSchema = false
)
@TypeConverters(DateConverter::class, UUIDConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun songDao(): SongDao
    abstract fun songBookDao(): SongBookDao
    abstract fun verseDao(): VerseDao
    abstract fun languageDao(): LanguageDao
}
