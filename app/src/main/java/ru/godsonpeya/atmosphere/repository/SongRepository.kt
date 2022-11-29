package ru.godsonpeya.atmosphere.repository

import kotlinx.coroutines.flow.Flow
import ru.godsonpeya.atmosphere.data.local.dao.LanguageDao
import ru.godsonpeya.atmosphere.data.local.dao.SongDao
import ru.godsonpeya.atmosphere.data.local.entity.Language
import ru.godsonpeya.atmosphere.data.local.entity.SongWithVerses
import javax.inject.Inject

class SongRepository @Inject constructor(
    private val songDao: SongDao,
    private val languageDao: LanguageDao,
) {
    fun getSongsBySongBookId(songBookId: Int): Flow<MutableList<SongWithVerses>> {
        try {
            val allSongById = songDao.getAllSongById(songBookId)
            return allSongById
        } catch (e: Exception) {
            e.printStackTrace()
        }
        throw RuntimeException()
    }

    fun searchSongs(songBookId: String): Flow<MutableList<SongWithVerses>> {
        try {
            val allSongById = songDao.searchSong(songBookId)
            return allSongById
        } catch (e: Exception) {
            e.printStackTrace()
        }
        throw RuntimeException()
    }

    fun getAllFavorites(): Flow<MutableList<SongWithVerses>> {
        try {
            val allSongById = songDao.getAllFavorites()
            return allSongById
        } catch (e: Exception) {
            e.printStackTrace()
        }
        throw RuntimeException()
    }

    fun getSong(songId: Int): SongWithVerses {
        return songDao.getById(songId)
    }

    suspend fun setFavorite(songId: Int,isFavorite:Boolean) {
        return songDao.setFavorite(songId, isFavorite)
    }

    fun getLanguages(song: SongWithVerses): Flow<MutableList<Language>> {
        return languageDao.getLangsByCode(song.song.code!!, song.song.languageId!!)
    }

    suspend fun getSongAnalogFromDB(langId: Int, songCode: String): SongWithVerses {
        return songDao.getAnalog(langId, songCode)
    }
}