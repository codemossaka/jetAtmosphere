package ru.godsonpeya.atmosphere.screens.favorite

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.godsonpeya.atmosphere.data.local.entity.SongWithVerses
import ru.godsonpeya.atmosphere.repository.SongRepository
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val songRepository: SongRepository) :
    ViewModel() {


    private var _songs = mutableStateOf<List<SongWithVerses>>(mutableListOf())
    val songs = _songs

    init {
        getSongs()
    }



    fun setFavorite(songId: Int, isFavorite: Boolean) {
        viewModelScope.launch {
            songRepository.setFavorite(songId, isFavorite)
        }
    }

    private fun getSongs() {
        viewModelScope.launch {
            songRepository.getAllFavorites().collectLatest {
                _songs.value = it
            }
        }
    }
}