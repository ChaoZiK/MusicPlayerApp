package com.example.musicplayer.backend

import android.content.Context
import android.media.MediaPlayer
import android.util.Log

import com.example.musicplayer.data.Song
import kotlin.random.Random

class MusicController(private val context: Context, private val onSongChanged: (Song) -> Unit) {

  private var mediaPlayer: MediaPlayer? = null
  private var songList: List<Song> = emptyList()
  private var currentSong: Song? = null

  fun shuffleAndPlay(songs: List<Song>) {
    songList = songs
    Log.d("MusicController", "Song list updated: ${songList.size} songs")
    if (songList.isNotEmpty()) {
      val shuffledSong = songList.random()
      playSong(shuffledSong)
    } else {
      Log.e("MusicController", "Song list is empty, cannot shuffle.")
    }
  }

  fun playSong(song: Song) {
    // Prevent duplicate playback
    if (currentSong == song && mediaPlayer?.isPlaying == true) {
      Log.d("MusicController", "Song is already playing: ${song.title}")
      return
    }

    stop() // Ensure the previous song is stopped
    Log.d("MusicController", "Playing song: ${song.title}")
    mediaPlayer = MediaPlayer().apply {
      try {
        setDataSource(song.path)
        prepare()
        start()
        currentSong = song
        onSongChanged(song)
        Log.d("MusicController", "Now playing: ${song.title}")
      } catch (e: Exception) {
        Log.e("MusicController", "Error playing song: ${e.message}", e)
        release()
      }
    }
  }

  fun togglePlayPause() {
    mediaPlayer?.let {
      if (it.isPlaying) {
        it.pause() // Pause the song
      } else {
        it.start() // Resume the song from the current position
      }
    }
  }

  fun continuePlaying() {
      mediaPlayer?.start()
  }

  fun pause() {
    mediaPlayer?.pause()
  }

  fun stop() {
    if (mediaPlayer != null) {
      Log.d("MusicController", "Stopping song: ${currentSong?.title}")
      try {
        mediaPlayer?.stop()
        mediaPlayer?.reset()
        mediaPlayer?.release()
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

