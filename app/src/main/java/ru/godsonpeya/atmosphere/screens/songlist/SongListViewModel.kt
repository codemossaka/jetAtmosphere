package ru.godsonpeya.atmosphere.screens.songlist

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.godsonpeya.atmosphere.data.dto.TabView
import ru.godsonpeya.atmosphere.data.local.entity.SongWithVerses
import ru.godsonpeya.atmosphere.repository.SongRepository
import javax.inject.Inject

@HiltViewModel
class SongListViewModel @Inject constructor(private val songRepository: SongRepository) :
    ViewModel() {
    private var _songs = mutableStateOf<List<SongWithVerses>>(mutableListOf())
    val songs = _songs

    val searchState: MutableState<Boolean> = mutableStateOf(true)

    fun getSongs(songBookId: Int) {
        viewModelScope.launch {
            songRepository.getSongsBySongBookId(songBookId).collectLatest {
                _songs.value = it
            }
        }
    }

    fun setFavorite(songId: Int, isFavorite: Boolean) {
        viewModelScope.launch {
            songRepository.setFavorite(songId, isFavorite)
        }
    }

    fun searchSongs(search: String) = viewModelScope.launch {
        songRepository.searchSongs(search).collectLatest {
            if (it.isNotEmpty())
                _songs.value = it
        }
    }

}