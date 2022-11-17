package ru.godsonpeya.atmosphere.repository

import android.util.Log
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.godsonpeya.atmosphere.data.local.dao.LanguageDao
import ru.godsonpeya.atmosphere.data.local.dao.SongBookDao
import ru.godsonpeya.atmosphere.data.local.dao.SongDao
import ru.godsonpeya.atmosphere.data.local.dao.VerseDao
import ru.godsonpeya.atmosphere.data.local.entity.ApiStatus
import ru.godsonpeya.atmosphere.data.local.entity.LanguageWithSongBook
import ru.godsonpeya.atmosphere.data.local.entity.SongBook
import ru.godsonpeya.atmosphere.data.local.entity.SongBookWithLanguage
import ru.godsonpeya.atmosphere.data.remote.dto.*
import ru.godsonpeya.atmosphere.data.remote.network.ApiService
import ru.godsonpeya.atmosphere.extentions.asLanguageDatabaseModel2
import ru.godsonpeya.atmosphere.extentions.asSongbookDatabaseModel2
import ru.godsonpeya.atmosphere.extentions.asSongsDatabaseModel2
import ru.godsonpeya.atmosphere.extentions.asVersesDatabaseModel2
import ru.godsonpeya.atmosphere.utils.SongBookEventBroadcast
import javax.inject.Inject

class SongBookRepository @Inject constructor(
    private val apiService: ApiService,
    private val languageDao: LanguageDao,
    private val songBookDao: SongBookDao,
    private val songDao: SongDao,
    private val verseDao: VerseDao,
    private val scope: CoroutineScope,
) {

    suspend fun getAllSongBooks(): Flow<MutableList<LanguageWithSongBook>> =
        languageDao.getAllSongsByLanguage().flowOn(Dispatchers.IO).conflate()

    suspend fun getAllDownloadedSongBooks(): Flow<MutableList<SongBookWithLanguage>> =
        songBookDao.getAllBook().flowOn(Dispatchers.IO).conflate()

    suspend fun deleteSongBook(songBookId: Int) {
        scope.launch {
            SongBookEventBroadcast.setStatus(ApiStatus.LOADING)
            deleteSongBookById(songBookId)
            SongBookEventBroadcast.setStatus(ApiStatus.DONE)
        }
    }

    suspend fun getSongBooksByLanguagesFromApi(): DataOrException<CustomSongBooks, Boolean, Exception> {
        return try {
            DataOrException(data = apiService.getSongBooksByLanguages())
        } catch (e: Exception) {
            DataOrException(e = e)
        }

    }

    suspend fun getSongBooksFromApi(): DataOrException<SongBooks, Boolean, Exception> {
        return try {
            DataOrException(data = apiService.getSongBooks())
        } catch (e: Exception) {
            DataOrException(e = e)
        }

    }

    suspend fun getSongsFromApi(songbookId: Int): DataOrException<Songs, Boolean, Exception> {
        return try {
            DataOrException(data = apiService.getSongs(songbookId))
        } catch (e: Exception) {
            DataOrException(e = e)
        }
    }

    suspend fun getSongFromApi(songId: Int): DataOrException<SongsItem, Boolean, Exception> {
        return try {
            DataOrException(data = apiService.getSong(songId))
        } catch (e: Exception) {
            DataOrException(e = e)
        }
    }

    suspend fun updateDownloadedSongBook() {
        scope.launch {
            try {
                SongBookEventBroadcast.setStatus(ApiStatus.LOADING)
                val songBooks = songBookDao.getAllBookWithoutFlow()
                songBooks.forEach { songBookWithLang ->
                    val songBook =
                        apiService.getSongBook(songBookWithLang.songbook.id!!).execute()
                            .body()
                    downloadSongs(songBook!!.asSongbookDatabaseModel2())
                }

                SongBookEventBroadcast.setStatus(ApiStatus.DONE)
            } catch (e: Exception) {
                SongBookEventBroadcast.setStatus(ApiStatus.ERROR)
            }

        }
    }

    fun refreshSongs() {
        scope.launch {
            try {
                SongBookEventBroadcast.setStatus(ApiStatus.LOADING)
                val language = apiService.getSongBooksByLanguages()
                if (language.isNotEmpty()) {
                    val songbooks = language.asSongbookDatabaseModel2()
                    songbooks.forEach {
                        it.id?.let { id ->
                            val byId = songBookDao.getById(id)
                            if (byId?.isDownLoaded == true) {
                                it.isDownLoaded = true
                            }
                        }
                    }
                    languageDao.insertWithSuspend(language.asLanguageDatabaseModel2())
                    songBookDao.insertWithSuspend(songbooks)
                }
                SongBookEventBroadcast.setStatus(ApiStatus.DONE)
            } catch (e: Exception) {
                SongBookEventBroadcast.setStatus(ApiStatus.ERROR)
            }
        }
    }

    suspend fun downloadSongsBySongBookId(songBook: SongBook) {
        scope.launch {
            try {
                SongBookEventBroadcast.setStatus(ApiStatus.LOADING)
                downloadSongs(songBook)
                SongBookEventBroadcast.setStatus(ApiStatus.DONE)
            } catch (e: Exception) {
                SongBookEventBroadcast.setStatus(ApiStatus.ERROR)
            }
        }
    }

    private suspend fun downloadSongs(songBookDto: SongBook) {
        val songs = apiService.getSongs(songBookDto.id!!)
        songDao.insertWithSuspend(songs.asSongsDatabaseModel2())
        verseDao.insertWithSuspend(songs.asVersesDatabaseModel2())
        songBookDto.isDownLoaded = true
        songBookDao.updateIsDownloaded(true, songBookDto.id!!)
    }

    //    	19972
    private suspend fun deleteSongBookById(songBookId: Int) {
        songDao.deleteAllBySongbookId(songBookId)
        songBookDao.updateIsDownloaded(false, songBookId)
    }
}