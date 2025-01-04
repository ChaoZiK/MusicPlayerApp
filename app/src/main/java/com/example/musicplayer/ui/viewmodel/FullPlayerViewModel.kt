package com.example.musicplayer.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicplayer.backend.MusicController
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.data.FavoriteDAO
import com.example.musicplayer.data.repository.PlayerRepository
import com.example.musicplayer.data.Song
import com.example.musicplayer.data.toFavoritesSong
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FullPlayerViewModel @Inject constructor(
    private val playerRepository: PlayerRepository,
    private val musicController: MusicController,
    private val favoriteDAO: FavoriteDAO,
    @ApplicationContext private val context: Context
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

    init {
        syncSystemVolume()
        musicController.registerVolumeObserver(context) {
            playerRepository.syncVolumeWithSystem()
        }
    }

    private fun showSnackbarMessage(message: String) {
        _snackbarMessage.value = message
    }

    fun clearSnackbarMessage() {
        _snackbarMessage.value = null
    }

    private fun syncSystemVolume() {
        playerRepository.syncVolumeWithSystem()
    }

    fun updateSystemVolume(volume: Float) {
        musicController.setVolume(volume)
        playerRepository.syncVolumeWithSystem()
    }

    fun updatePlaylist(songs: List<Song>) {
        playerRepository.updatePlaylist(songs)
    }

    fun playSongByIndex(index: Int) {
        val song = playerRepository.playlist.value.getOrNull(index) ?: return

        if (song != playerRepository.currentSong.value) {
            musicController.stop()
            playerRepository.updateSongByIndex(index)
            musicController.playSong(song)
            playerRepository.togglePlayPause()
        }
    }

    fun togglePlayPause() {
        playerRepository.togglePlayPause()
    }

    fun toggleFavorite() {
        val song = currentSong.value ?: return
        viewModelScope.launch {
            val favorite = favoriteDAO.getFavoriteBySongId(song.id)
            if (favorite == null) {
                favoriteDAO.insertFavorite(song.toFavoritesSong(System.currentTimeMillis()))
                playerRepository.toggleFavorite(song)
            } else {
                favoriteDAO.deleteFavorite(favorite)
                playerRepository.toggleFavorite(song)
            }
        }
    }

    fun playNext() {
        val nextSong = playerRepository.nextSong() ?: return

        musicController.stop()
        musicController.playSong(nextSong)
        playerRepository.togglePlayPause()
    }

    fun playPrevious() {
        val previousSong = playerRepository.previousSong() ?: return

        musicController.stop()
        musicController.playSong(previousSong)
        playerRepository.togglePlayPause()
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

    fun shuffleAndPlay() {
        playerRepository.shuffle()
        playerRepository.playOrShuffle()
    }

    fun playFirstSong() {
        playerRepository.playFirstSong()
        playerRepository.playOrShuffle()
    }
}