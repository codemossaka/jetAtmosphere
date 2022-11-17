package ru.godsonpeya.atmosphere.data.local.dao

import androidx.room.*
import ru.godsonpeya.atmosphere.data.local.entity.SongBook
import ru.godsonpeya.atmosphere.data.local.entity.SongBookWithLanguage
import kotlinx.coroutines.flow.Flow

@Dao
interface SongBookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWithSuspend(obj: List<SongBook>): List<Long>

    @Transaction
    @Query("SELECT * FROM songBook WHERE isDownLoaded=1 ORDER BY name ASC")
    fun getAllBook(): Flow<MutableList<SongBookWithLanguage>>

    @Transaction
    @Query("SELECT * FROM songBook WHERE isDownLoaded=1 ORDER BY name ASC")
    suspend fun getAllBookWithoutFlow(): MutableList<SongBookWithLanguage>

    @Query("UPDATE songbook SET isDownLoaded=:value WHERE id=:songBookId")
    suspend fun updateIsDownloaded(value: Boolean, songBookId: Int)

    @Query("SELECT * FROM songBook WHERE id =:id LIMIT 1")
    suspend fun getById(id: Int): SongBook?
}
