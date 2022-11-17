package ru.godsonpeya.atmosphere.repository

import ru.godsonpeya.atmosphere.data.local.dao.LanguageDao
import ru.godsonpeya.atmosphere.data.local.dao.SongDao
import ru.godsonpeya.atmosphere.data.local.entity.Language
import ru.godsonpeya.atmosphere.data.local.entity.SongWithVerses
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SongRepository @Inject constructor(
    private val songDao: SongDao,
    private val languageDao: LanguageDao,
) {
    fun getSongsBySongBookId(songBookId: Int): Flow<MutableList<SongWithVerses>> {
        try {
//            SongBookEventBroadcast.setStatus(ApiStatus.LOADING)
//            SongBookEventBroadcast.setStatus(ApiStatus.DONE)
            return songDao.getAllSongById(songBookId)
        } catch (e: Exception) {
//            SongBookEventBroadcast.setStatus(ApiStatus.ERROR)
            e.printStackTrace()
        }
        throw RuntimeException()
    }

    fun getSong(songId: Int): SongWithVerses {
        return songDao.getById(songId)
    }

    fun getLanguages(code: String): Flow<MutableList<Language>> {
        return languageDao.getLangsByCode(code)
    }

//    suspend fun getSongAnalogFromDB(langId: Int, songCode: String): Flow<SongWithVerses> {
//        return songDao.getAnalog(langId, songCode)
//    }
}