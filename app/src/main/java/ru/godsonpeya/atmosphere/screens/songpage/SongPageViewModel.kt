package ru.godsonpeya.atmosphere.screens.songpage

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.godsonpeya.atmosphere.data.local.entity.Language
import ru.godsonpeya.atmosphere.data.local.entity.SongWithVerses
import ru.godsonpeya.atmosphere.media.exoplayer.MediaPlayerServiceConnection
import ru.godsonpeya.atmosphere.media.exoplayer.isPlaying
import ru.godsonpeya.atmosphere.repository.SongRepository
import javax.inject.Inject

@HiltViewModel
class SongPageViewModel @Inject constructor(
    private val songRepository: SongRepository,
    private val serviceConnection: MediaPlayerServiceConnection,
) :
    ViewModel() {
    val song = mutableStateOf(SongWithVerses())

    val languages = mutableStateOf<List<Language>>(mutableListOf())


    val currentPlayingAudio = serviceConnection.currentPlayingAudio
    private val isConnected = serviceConnection.isConnected
    lateinit var rootMediaId: String
    var currentPlayBackPosition by mutableStateOf(0L)
    private var updatePosition = true
    private val playbackState = serviceConnection.plaBackState
    val isAudioPlaying: Boolean
        get() = playbackState.value?.isPlaying == true


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

    fun playOrToggleSong(currentAudio: SongWithVerses) {
//        serviceConnection.playAudio(audioList)
        if (currentAudio.song.id == currentPlayingAudio.value?.song?.id) {
            if (isAudioPlaying) {
                serviceConnection.transportControl.pause()
            } else {
                serviceConnection.transportControl.play()
            }


        } else {
            serviceConnection.transportControl
                .playFromMediaId(
                    currentAudio.song.id.toString(),
                    null
                )
        }


    }


}