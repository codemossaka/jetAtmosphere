package ru.godsonpeya.atmosphere.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.godsonpeya.atmosphere.data.local.entity.Verse

@Dao
interface VerseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWithSuspend(obj: List<Verse>): List<Long>

    @Query("SELECT * FROM verse WHERE id =:id LIMIT 1")
    fun getById(id: Long): LiveData<Verse>
}
