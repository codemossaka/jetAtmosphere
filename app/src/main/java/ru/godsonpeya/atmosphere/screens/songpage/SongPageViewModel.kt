package ru.godsonpeya.atmosphere.screens.songpage

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.godsonpeya.atmosphere.data.local.entity.Language
import ru.godsonpeya.atmosphere.data.local.entity.SongWithVerses
import ru.godsonpeya.atmosphere.repository.SongRepository
import javax.inject.Inject

@HiltViewModel
class SongPageViewModel @Inject constructor(private val songRepository: SongRepository) :
    ViewModel() {
    val song = mutableStateOf(SongWithVerses())


    val languages = mutableStateOf<List<Language>>(mutableListOf())

    fun getSong(songId: Int) = viewModelScope.launch(Dispatchers.IO) {
        song.value = songRepository.getSong(songId)
    }

    fun getAnalog(result: Int, code: String) = viewModelScope.launch {
        song.value = songRepository.getSongAnalogFromDB(result, code)
    }

    fun getLanguages(song: SongWithVerses) = viewModelScope.launch {
        songRepository.getLanguages(song).collectLatest {
            languages.value = it
        }
    }

}