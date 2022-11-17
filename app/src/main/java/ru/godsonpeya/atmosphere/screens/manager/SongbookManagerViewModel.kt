package ru.godsonpeya.atmosphere.screens.manager

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.godsonpeya.atmosphere.data.local.entity.ApiStatus
import ru.godsonpeya.atmosphere.data.local.entity.LanguageWithSongBook
import ru.godsonpeya.atmosphere.data.local.entity.SongBook
import ru.godsonpeya.atmosphere.repository.SongBookRepository
import ru.godsonpeya.atmosphere.utils.SongBookEventBroadcast.watchSongBookStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SongbookManagerViewModel @Inject constructor(private val songBookRepository: SongBookRepository) :
    ViewModel() {

    private val _status = mutableStateOf(ApiStatus.IDLE)
    val status = _status

    private val _expandedLanguageList = MutableStateFlow(listOf<Int>())
    val expandedLanguageList: StateFlow<List<Int>> get() = _expandedLanguageList


    private val _songBooks = MutableStateFlow<MutableList<LanguageWithSongBook>>(mutableListOf())
    val songBooks = _songBooks.asStateFlow()

    private val _downloadingSongBook = MutableStateFlow(SongBook(name = "Sync with server"))
    val downloadingSongBook = _downloadingSongBook.asStateFlow()

    init {
        viewModelScope.launch {
            watchStatus()
            getAllSongBooks()
        }
    }

    private fun CoroutineScope.watchStatus() = launch {
        watchSongBookStatus.collectLatest { state ->
            _status.value = state
        }
    }

    private fun CoroutineScope.getAllSongBooks() = launch {
        songBookRepository.getAllSongBooks().distinctUntilChanged().collectLatest {
            _songBooks.value = it
        }
    }

    fun languageClick(languageId: Int) {
        _expandedLanguageList.value = _expandedLanguageList.value.toMutableList().also { list ->
            if (list.contains(languageId)) {
                list.remove(languageId)
            } else {
                list.add(languageId)
            }
        }
    }

    fun downLoadSongBook(songBook: SongBook) {
        _downloadingSongBook.value=songBook
        viewModelScope.launch {
            songBookRepository.downloadSongsBySongBookId(songBook)
        }
    }

    fun syncData() {
        viewModelScope.launch {
            songBookRepository.refreshSongs()
        }
    }

    fun deleteSongBook(songBookId: Int) {
        viewModelScope.launch {
            songBookRepository.deleteSongBook(songBookId)
        }
    }
}