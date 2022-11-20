package ru.godsonpeya.atmosphere.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.godsonpeya.atmosphere.data.local.entity.Language
import ru.godsonpeya.atmosphere.data.local.entity.LanguageWithDownloadedSongBook
import ru.godsonpeya.atmosphere.data.local.entity.LanguageWithSongBook

@Dao
interface LanguageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWithSuspend(obj: List<Language>): List<Long>

    @Transaction
    @Query("SELECT * FROM language ORDER BY name ASC")
    fun getAllSongsByLanguage(): Flow<MutableList<LanguageWithSongBook>>

    @Transaction
    @Query("SELECT DISTINCT l.id ,l.* FROM language l JOIN songbook sb ON sb.languageId = l.id WHERE sb.isDownLoaded = 1 ORDER BY l.name ASC")
    fun getAllDownloaded(): LiveData<List<LanguageWithDownloadedSongBook>>

    @Transaction
    @Query("SELECT * FROM language WHERE id =:id LIMIT 1")
    fun getById(id: Long): LiveData<LanguageWithSongBook>

    @Transaction
    @Query("SELECT distinct l.id,l.code , l.name FROM language l inner join song s on l.id = s.languageId where s.code = :songCode AND l.id IS NOT :languageId order by l.id")
    fun getLangsByCode(songCode: String, languageId: Int): Flow<MutableList<Language>>
}
