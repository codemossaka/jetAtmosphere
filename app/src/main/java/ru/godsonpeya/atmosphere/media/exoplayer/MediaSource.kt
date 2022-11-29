package ru.godsonpeya.atmosphere.media.exoplayer

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaBrowserCompat.MediaItem.FLAG_PLAYABLE
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import ru.godsonpeya.atmosphere.repository.SongRepository
import javax.inject.Inject

class MediaSource @Inject constructor(private val repository: SongRepository) {
    private val onReadyListeners: MutableList<OnReadyListener> = mutableListOf()

    var audioMediaMetaData: List<MediaMetadataCompat> = emptyList()

    private var state: AudioSourceState = AudioSourceState.STATE_CREATED
        set(value) {
            if (
                value == AudioSourceState.STATE_CREATED
                || value == AudioSourceState.STATE_ERROR
            ) {
                synchronized(onReadyListeners) {
                    field = value
                    onReadyListeners.forEach { listener: OnReadyListener ->
                        listener.invoke(isReady)
                    }
                }
            } else {
                field = value
            }


        }

    suspend fun load() {
        state = AudioSourceState.STATE_INITIALIZING
        Dispatchers.IO
        val data = repository.getAllFavorites().collectLatest {
//            songsTest = it
            audioMediaMetaData = it.map { song ->
                MediaMetadataCompat.Builder()
                    .putString(MediaMetadataCompat.METADATA_KEY_TITLE, song.song.name)
                    .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_TITLE, song.song.name)
                    .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, song.song.name)
                    .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_SUBTITLE, song.song.name)
                    .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, song.song.id.toString())
                    .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI, song.song.audioUrl)
                    .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON_URI, song.song.imageUrl)
                    .putString(MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI, song.song.imageUrl)
                    .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_DESCRIPTION, song.song.name)
                    .build()
            }
        }
        Dispatchers.Main
        state = AudioSourceState.STATE_INITIALIZED

    }

    fun asMediaSource(dataSource: CacheDataSource.Factory):
            ConcatenatingMediaSource {
        val concatenatingMediaSource = ConcatenatingMediaSource()

        audioMediaMetaData.forEach { mediaMetadataCompat ->
            val mediaItem = MediaItem.fromUri(
                mediaMetadataCompat.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI)
            )

            val mediaSource = ProgressiveMediaSource
                .Factory(dataSource)
                .createMediaSource(mediaItem)

            concatenatingMediaSource.addMediaSource(mediaSource)


        }
        return concatenatingMediaSource

    }


    fun asMediaItem() = audioMediaMetaData.map { metaData ->
        val description = MediaDescriptionCompat.Builder()
            .setTitle(metaData.description.title)
            .setMediaId(metaData.description.mediaId)
            .setSubtitle(metaData.description.subtitle)
            .setMediaUri(metaData.description.mediaUri)
            .build()
        MediaBrowserCompat.MediaItem(description, FLAG_PLAYABLE)

    }.toMutableList()


    fun refresh() {
        onReadyListeners.clear()
        state = AudioSourceState.STATE_CREATED
    }


    fun whenReady(listener: OnReadyListener): Boolean {
        return if (
            state == AudioSourceState.STATE_CREATED
            || state == AudioSourceState.STATE_INITIALIZING
        ) {
            onReadyListeners += listener
            false
        } else {
            listener.invoke(isReady)
            true
        }

    }


    private val isReady: Boolean
        get() = state == AudioSourceState.STATE_INITIALIZED


}

enum class AudioSourceState {
    STATE_CREATED,
    STATE_INITIALIZING,
    STATE_INITIALIZED,
    STATE_ERROR,
}

typealias OnReadyListener = (Boolean) -> Unit