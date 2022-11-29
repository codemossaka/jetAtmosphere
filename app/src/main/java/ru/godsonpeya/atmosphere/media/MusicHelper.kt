package ru.godsonpeya.atmosphere.media

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Message
import android.view.View
import android.widget.SeekBar
import androidx.compose.runtime.MutableState
import ru.godsonpeya.atmosphere.data.local.entity.SongWithVerses

class MusicHelper(private val data: MutableState<SongWithVerses>) {

    private var mp: MediaPlayer? = null
    private var totalTime: Int = 0


//    fun init() {
//
//        playerInit(data.value)
//        mp?.let {
//            if (it.isPlaying) {
//                it.stop()
//            }
//        }
//    }

//    private fun playerInit(song: Song) {
//        binding.btnPlay.visibility = View.GONE
//        binding.progressBarPlayer.visibility = View.VISIBLE
//
//        mp = MediaPlayer().apply {
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//                setAudioAttributes(
//                    AudioAttributes.Builder()
//                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
//                        .setUsage(AudioAttributes.USAGE_MEDIA)
//                        .build()
//                )
//            }
//            isLooping = false
//            setVolume(0.5f, 0.5f)
//            setDataSource(MEDIA_URL + song.audioUrl)
//            prepareAsync()
//            primarySeekBarProgressUpdater()
//        }
//
//        mp?.setOnPreparedListener {
//            totalTime = mp?.duration!!
//            binding.progressBarPlayer.visibility = View.GONE
//            binding.btnPlay.visibility = View.VISIBLE
//            Timber.i("nous sommes la $totalTime")
//        }
//
//        // Position Bar
//        binding.seekBar.max = totalTime
//        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
//            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
//                if (fromUser) {
//                    mp?.seekTo(progress)
//                }
//            }
//
//            override fun onStartTrackingTouch(p0: SeekBar?) {}
//
//            override fun onStopTrackingTouch(p0: SeekBar?) {}
//        })
//
//        Thread {
//            while (mp != null) {
//                try {
//                    val msg = Message()
//                    try {
//                        msg.what = mp?.currentPosition!!
//                    } catch (e: IllegalStateException) {
//                        Timber.tag(SongPageFragment::class.java.name).e(e.message.toString())
//                    }
//                    handler.sendMessage(msg)
//                    Thread.sleep(1000)
//                } catch (e: InterruptedException) {
//                    Timber.tag(SongPageFragment::class.java.name).e(e.message.toString())
//                }
//            }
//        }.start()
//    }
}