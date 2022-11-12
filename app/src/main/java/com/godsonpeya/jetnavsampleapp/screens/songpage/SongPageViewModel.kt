package com.godsonpeya.jetnavsampleapp.screens.songpage

import androidx.lifecycle.ViewModel
import com.godsonpeya.jetnavsampleapp.model.Songs
import com.godsonpeya.jetnavsampleapp.model.SongsItem
import com.godsonpeya.jetnavsampleapp.repository.SongBookRepository
import com.godsonpeya.jetweatherforcast.data.DataOrException
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SongPageViewModel @Inject constructor(private val songBookRepository: SongBookRepository) :
    ViewModel() {

    suspend fun getSong(songId: Int): DataOrException<SongsItem, Boolean, Exception> {
        return songBookRepository.getSongFromApi(songId)
    }
}