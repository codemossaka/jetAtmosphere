package ru.godsonpeya.atmosphere.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.godsonpeya.atmosphere.data.local.entity.Song
import ru.godsonpeya.atmosphere.data.local.entity.SongWithVerses

@Dao
interface SongDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWithSuspend(songs: List<Song>): List<Long>

    @Query("DELETE FROM song")
    suspend fun deleteAllWithSuspend()

    @Query("DELETE FROM song WHERE songbookId=:songbookId")
    suspend fun deleteAllBySongbookId(songbookId: Int)

    @Query("SELECT * FROM song WHERE id =:id LIMIT 1")
    fun getById(id: Int): SongWithVerses

    @Query("UPDATE song SET isFavorite=:isFavorite WHERE id=:songId")
    suspend fun setFavorite(songId: Int, isFavorite: Boolean)

    @Transaction
    @Query("SELECT * FROM song s  WHERE languageId =:langId AND s.code =:songCode LIMIT 1")
    suspend fun getAnalog(langId: Int, songCode: String): SongWithVerses

    @Transaction
    @Query("SELECT * FROM song WHERE songbookId =:songBookId ORDER BY number")
    fun getAllSongById(songBookId: Int): Flow<MutableList<SongWithVerses>>

    @Transaction
    @Query("SELECT DISTINCT sb.id,  s.*  FROM song s JOIN songBook sb ON s.songbookId=sb.id JOIN verse v ON s.id=v.songId WHERE s.name LIKE '%'|| :search ||'%' OR  v.line  LIKE '%'|| :search ||'%' OR  s.number  LIKE '%'|| :search ||'%' ORDER BY s.number")
    fun searchSong(search: String): Flow<MutableList<SongWithVerses>>

    @Transaction
    @Query("SELECT * FROM song ORDER BY number")
    fun getAllSong(): Flow<MutableList<SongWithVerses>>

    @Transaction
    @Query("SELECT * FROM song WHERE isFavorite=1 ORDER BY number")
    fun getAllFavorites(): Flow<MutableList<SongWithVerses>>

    @Query("SELECT COUNT(id) FROM song WHERE songbookId =:songBookId")
    fun getCount(songBookId: Int): Int
}
