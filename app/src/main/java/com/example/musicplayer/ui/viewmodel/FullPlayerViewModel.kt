package com.example.musicplayer.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private val _snackbarMessage = MutableLiveData<String?>()
    val snackbarMessage: LiveData<String?> = _snackbarMessage

    fun showSnackbarMessage(message: String) {
        _snackbarMessage.value = message
    }

    fun clearSnackbarMessage() {
        _snackbarMessage.value = null
    }

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
        playerRepository.togglePlayPause()
    }

    fun playNext() {
        val nextSong = playerRepository.nextSong()
        if (nextSong == null) {
            Log.d("FullPlayerViewModel", "No next song available.")
            return
        }

        Log.d("FullPlayerViewModel", "Playing next song: ${nextSong.title}")
        musicController.stop() // Stop the current song
        musicController.playSong(nextSong) // Play the next song
        playerRepository.togglePlayPause() // Start progress updates
    }


    fun playPrevious() {
        val previousSong = playerRepository.previousSong()
        if (previousSong == null) {
            Log.d("FullPlayerViewModel", "No previous song available.")
            return
        }

        Log.d("FullPlayerViewModel", "Playing previous song: ${previousSong.title}")
        musicController.stop() // Stop the current song
        musicController.playSong(previousSong) // Play the previous song
        playerRepository.togglePlayPause() // Start progress updates
    }

    fun seekTo(newProgress: Float) {
        playerRepository.seekToProgress(newProgress)
    }

    fun toggleShuffle() {
        playerRepository.toggleShuffle()
        val message = if (playerRepository.isShuffleEnabled.value) {
            "Shuffle enabled"
        } else {
            "Shuffle disabled"
        }
        showSnackbarMessage(message)
    }

    fun toggleRepeat() {
        playerRepository.toggleRepeat()
        val message = when (playerRepository.repeatMode.value) {
            PlayerRepository.RepeatMode.NONE -> "Repeat mode: None"
            PlayerRepository.RepeatMode.ALL -> "Repeat mode: All"
            PlayerRepository.RepeatMode.ONE -> "Repeat mode: One"
        }
        showSnackbarMessage(message)
    }

    fun updateProgress(progress: Float) {
        playerRepository.updateProgress(progress)
    }

    fun toggleFavorite() {
        playerRepository.toggleFavorite(currentSong.value)
    }
}
