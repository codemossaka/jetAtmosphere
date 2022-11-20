package ru.godsonpeya.atmosphere.screens.songbook

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import ru.godsonpeya.atmosphere.data.local.entity.ApiStatus
import ru.godsonpeya.atmosphere.data.local.entity.SongBookWithLanguage
import ru.godsonpeya.atmosphere.repository.SongBookRepository
import ru.godsonpeya.atmosphere.utils.SongBookEventBroadcast
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val songBookRepository: SongBookRepository) :
    ViewModel() {


    private val _status = mutableStateOf(ApiStatus.IDLE)
    val status = _status

    private val _job = mutableStateOf<Job>(Job())

    // songBooks from local db
    private val _songBooks = MutableStateFlow<MutableList<SongBookWithLanguage>>(mutableListOf())
    val songBooks = _songBooks.asStateFlow()

    init {
        watchStatus()
        getAllDownloadedSongBooks()
    }

    private fun watchStatus() = viewModelScope.launch {
        SongBookEventBroadcast.watchSongBookStatus.collectLatest { state ->
            _status.value = state
        }
    }

    private fun getAllDownloadedSongBooks() {
        viewModelScope.launch {
            songBookRepository.getAllDownloadedSongBooks().distinctUntilChanged().collectLatest {
                _songBooks.value = it.toMutableList()
            }
        }
    }

    fun cancelSync() {
        viewModelScope.launch {
            _job.value.cancel()
        }
    }

    fun deleteSongBook(songBookId: Int) {
        viewModelScope.launch{
            songBookRepository.deleteSongBook(songBookId)
        }
    }

    fun updateSongBook(songBookId: Int) {
        viewModelScope.launch {
            _job.value  = songBookRepository.updateDownloadedSongBook(songBookId)
        }
    }
}