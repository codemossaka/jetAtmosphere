package com.godsonpeya.jetnavsampleapp.screens.songlist

import androidx.lifecycle.ViewModel
import com.godsonpeya.jetnavsampleapp.model.Songs
import com.godsonpeya.jetnavsampleapp.repository.SongBookRepository
import com.godsonpeya.jetweatherforcast.data.DataOrException
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SongListViewModel @Inject constructor(private val songBookRepository: SongBookRepository) : ViewModel() {

    suspend fun getSongs(songBookId: Int): DataOrException<Songs, Boolean, Exception> {
        return songBookRepository.getSongsFromApi(songBookId)
    }
}