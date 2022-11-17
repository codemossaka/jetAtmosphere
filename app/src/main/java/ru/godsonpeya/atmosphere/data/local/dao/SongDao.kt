package ru.godsonpeya.atmosphere.data.local.dao

import androidx.room.*
import ru.godsonpeya.atmosphere.data.local.entity.Song
import ru.godsonpeya.atmosphere.data.local.entity.SongWithVerses
import kotlinx.coroutines.flow.Flow

@Dao
interface SongDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWithSuspend(obj: List<Song>): List<Long>

    @Query("DELETE FROM song")
    suspend fun deleteAllWithSuspend()

    @Query("DELETE FROM song WHERE songbookId=:songbookId")
    suspend fun deleteAllBySongbookId(songbookId: Int)

    @Query("SELECT * FROM song WHERE id =:id LIMIT 1")
    fun getById(id: Int): SongWithVerses

    @Transaction
    @Query("SELECT * FROM song s  WHERE languageId =:langId AND s.code =:songCode LIMIT 1")
    suspend fun getAnalog(langId: Int, songCode: String): SongWithVerses

    @Transaction
    @Query("SELECT * FROM song WHERE songbookId =:songBookId ORDER BY number")
    fun getAllSongById(songBookId: Int): Flow<MutableList<SongWithVerses>>

    @Query("SELECT COUNT(id) FROM song WHERE songbookId =:songBookId")
    fun getCount(songBookId: Int): Int
}
