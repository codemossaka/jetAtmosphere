package com.godsonpeya.jetnavsampleapp.repository

import com.godsonpeya.jetnavsampleapp.model.SongBooks
import com.godsonpeya.jetnavsampleapp.model.Songs
import com.godsonpeya.jetnavsampleapp.model.SongsItem
import com.godsonpeya.jetnavsampleapp.network.ApiService
import com.godsonpeya.jetweatherforcast.data.DataOrException
import javax.inject.Inject

class SongBookRepository @Inject constructor(private val apiService: ApiService) {
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

    suspend fun getSongFromApi(songd: Int): DataOrException<SongsItem, Boolean, Exception> {
        return try {
            DataOrException(data = apiService.getSong(songd))
        } catch (e: Exception) {
            DataOrException(e = e)
        }
    }
}