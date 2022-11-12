package com.godsonpeya.jetnavsampleapp.screens.songbook

import androidx.lifecycle.ViewModel
import com.godsonpeya.jetnavsampleapp.model.SongBooks
import com.godsonpeya.jetnavsampleapp.repository.SongBookRepository
import com.godsonpeya.jetweatherforcast.data.DataOrException
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val songBookRepository: SongBookRepository) :
    ViewModel() {

    suspend fun getSongBooks(): DataOrException<SongBooks, Boolean, Exception> {
        return songBookRepository.getSongBooksFromApi()
    }
}