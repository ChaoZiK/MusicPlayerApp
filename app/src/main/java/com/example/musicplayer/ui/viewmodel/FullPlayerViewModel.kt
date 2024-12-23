package com.example.musicplayer.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.musicplayer.backend.MusicController
import com.example.musicplayer.data.Song
import com.example.musicplayer.data.repository.PlayerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FullPlayerViewModel @Inject constructor(
    private val playerRepository: PlayerRepository,
    private val musicController: MusicController // Inject MusicController
) : ViewModel() {

    val isPlaying = playerRepository.isPlaying
    val currentSong = playerRepository.currentSong
    val progress = playerRepository.progress
    val currentTime = playerRepository.currentTime
    val totalTime = playerRepository.totalTime
    val artUri = playerRepository.artUri
    val songTitle = playerRepository.songTitle
    val artist = playerRepository.artist
    val isFavorite = playerRepository.isFavorite
    val isShuffleEnabled = playerRepository.isShuffleEnabled
    val repeatMode = playerRepository.repeatMode
    val volume = playerRepository.volume

    fun updateVolume(newVolume: Float) {
        playerRepository.updateVolume(newVolume)
    }

    fun updatePlaylist(songs: List<Song>) {
        playerRepository.updatePlaylist(songs)
    }

    fun playSongByIndex(index: Int) {
        val song = playerRepository.playlist.value.getOrNull(index)
        if (song == null) {
            Log.d("FullPlayerViewModel", "Invalid index: $index. Cannot play song.")
            return
        }

        if (song != playerRepository.currentSong.value) {
            musicController.stop()
            playerRepository.updateSongByIndex(index)
            musicController.playSong(song)
            playerRepository.togglePlayPause() // Ensure progress updates start
        }
    }



    fun togglePlayPause() {
        if (playerRepository.isPlaying.value) {
            musicController.pause()
        } else {
            playerRepository.currentSong.value?.let { musicController.playSong(it) }
        }
        playerRepository.togglePlayPause()
    }

    fun playNext() {
        val nextSong = playerRepository.nextSong()
        if (nextSong == null) {
            Log.d("FullPlayerViewModel", "No next song available.")
            return
        }

        if (nextSong == playerRepository.currentSong.value) {
            Log.d("FullPlayerViewModel", "Next song is already playing: ${nextSong.title}. Skipping.")
            return
        }

        Log.d("FullPlayerViewModel", "Playing next song: ${nextSong.title}")

        musicController.stop() // Stop the current song
        playerRepository.updateSong(nextSong)
        musicController.playSong(nextSong) // Play the next song
    }


    fun playPrevious() {
        val previousSong = playerRepository.previousSong()
        if (previousSong == null) {
            Log.d("FullPlayerViewModel", "No previous song available.")
            return
        }

        if (previousSong == playerRepository.currentSong.value) {
            Log.d("FullPlayerViewModel", "Previous song is already playing: ${previousSong.title}. Skipping.")
            return
        }

        Log.d("FullPlayerViewModel", "Playing previous song: ${previousSong.title}")

        musicController.stop() // Stop the current song
        playerRepository.updateSong(previousSong)
        musicController.playSong(previousSong) // Play the previous song
    }

    fun toggleShuffle() {
        playerRepository.toggleShuffle()
    }

    fun toggleRepeat() {
        playerRepository.toggleRepeat()
    }

    fun updateProgress(progress: Float) {
        playerRepository.updateProgress(progress)
    }

    fun toggleFavorite() {
        playerRepository.toggleFavorite(currentSong.value)
    }
}
