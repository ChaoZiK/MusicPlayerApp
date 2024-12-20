package com.example.musicplayer.backend

import android.content.Context
import android.media.MediaPlayer
import android.util.Log

import com.example.musicplayer.data.Song
import kotlin.random.Random

class MusicController(private val context: Context) {


  private var mediaPlayer: MediaPlayer? = null
  private var songList: List<Song> = emptyList()
  private var currentSong: Song? = null


  fun shuffleAndPlay(songs: List<Song>) {
    songList = songs
    Log.d("MusicController", "Song list updated: ${songList.size} songs")
    if (songList.isNotEmpty()) {
      val shuffledSong = songList[Random.nextInt(songList.size)]
      playSong(shuffledSong)
    } else {
      Log.e("MusicController", "Song list is empty, cannot shuffle.")
    }
  }
  fun playSong(song: Song) {
    stop()
    mediaPlayer = MediaPlayer().apply {
      try {
        setDataSource(song.path)
        prepare()
        start()
        currentSong = song

      } catch (e: Exception) {
        Log.e("MusicController", "Error playing song: ${e.message}", e)
      }
    }
  }
  fun pause() {
    mediaPlayer?.pause()
  }
  fun stop() {
    mediaPlayer?.apply {
      if (isPlaying) stop()
      release()
    }
    mediaPlayer = null
    currentSong = null
  }
  fun isPlaying(): Boolean {
    return mediaPlayer?.isPlaying == true
  }
}
