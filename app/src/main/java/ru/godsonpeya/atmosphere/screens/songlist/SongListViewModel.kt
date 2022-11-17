package ru.godsonpeya.atmosphere.screens.songlist

import androidx.lifecycle.ViewModel
import ru.godsonpeya.atmosphere.data.local.entity.SongWithVerses
import ru.godsonpeya.atmosphere.repository.SongRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SongListViewModel @Inject constructor(private val songRepository: SongRepository) :
    ViewModel() {
    private val _songs = MutableStateFlow<MutableList<SongWithVerses>>(mutableListOf())
    val songs = _songs.asStateFlow()

//    private fun getSongsBySongBookId() {
//        viewModelScope.launch {
//            songRepository.getSongsBySongBookId(songBookId).distinctUntilChanged()
//                .collectLatest { songList ->
//                    _songs.value = songList
//                }
//        }
//    }

    suspend fun getSongs(songBookId: Int): Flow<MutableList<SongWithVerses>> {
        return songRepository.getSongsBySongBookId(songBookId)
    }
}