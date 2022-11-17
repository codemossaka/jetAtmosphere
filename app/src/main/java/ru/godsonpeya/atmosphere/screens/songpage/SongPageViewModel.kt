package ru.godsonpeya.atmosphere.screens.songpage

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.godsonpeya.atmosphere.data.local.entity.SongWithVerses
import ru.godsonpeya.atmosphere.repository.SongRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SongPageViewModel @Inject constructor(private val songRepository: SongRepository) :
    ViewModel() {
    var song by mutableStateOf(SongWithVerses())
        private set

    fun getSong(songId: Int) = viewModelScope.launch(Dispatchers.IO) {
        song = songRepository.getSong(songId)
    }
}