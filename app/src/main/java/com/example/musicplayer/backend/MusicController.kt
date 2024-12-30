package com.example.musicplayer.backend

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.util.Log

import com.example.musicplayer.data.Song

class MusicController(context: Context, private val onSongChanged: (Song) -> Unit) {

    private var mediaPlayer: MediaPlayer? = null
    private var songList: List<Song> = emptyList()
    private var currentSong: Song? = null

    private val audioManager: AudioManager =
        context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    fun setVolume(volume: Float) {
        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        val systemVolume = (volume * maxVolume).toInt().coerceIn(0, maxVolume)
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, systemVolume, 0)
    }

    fun getVolume(): Float {
        val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        return currentVolume / maxVolume.toFloat()
    }

    fun registerVolumeObserver(context: Context, onVolumeChange: (Float) -> Unit) {
        val contentResolver = context.contentResolver
        val observer = object : android.database.ContentObserver(null) {
            override fun onChange(selfChange: Boolean) {
                val currentVolume = getVolume()
                onVolumeChange(currentVolume)
            }
        }
        contentResolver.registerContentObserver(
            android.provider.Settings.System.CONTENT_URI,
            true,
            observer
        )
    }

    fun shuffleAndPlay(songs: List<Song>) {
        songList = songs
        if (songList.isNotEmpty()) {
            val shuffledSong = songList.random()
            playSong(shuffledSong)
        }
    }

    fun playSong(song: Song) {
        if (currentSong == song && mediaPlayer?.isPlaying == true) {
            return
        }

        stop()
        mediaPlayer = MediaPlayer().apply {
            try {
                setDataSource(song.path)
                prepare()
                start()
                currentSong = song
                onSongChanged(song)
            } catch (e: Exception) {
                release()
            }
        }
    }

    fun togglePlayPause() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.pause()
            } else {
                it.start()
            }
        }
    }

    fun continuePlaying() {
        mediaPlayer?.start()
    }

    fun stop() {
        mediaPlayer?.apply {
            try {
                stop()
                reset()
                release()
            } catch (e: Exception) {
                Log.e("MusicController", "Error stopping player: ${e.message}")
            } finally {
                mediaPlayer = null
            }
        }
        currentSong = null
    }


    fun isPlaying(): Boolean {
        return mediaPlayer?.isPlaying == true
    }

    fun seekTo(positionMillis: Int) {
        mediaPlayer?.let {
            try {
                it.seekTo(positionMillis)
            } catch (e: Exception) {
                Log.e("MusicController", "Error seeking to position: ${e.message}", e)
            }
        }
    }

}

