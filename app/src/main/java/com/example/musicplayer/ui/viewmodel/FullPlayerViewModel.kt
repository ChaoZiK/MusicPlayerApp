package com.example.musicplayer.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.data.FavoriteDAO
import com.example.musicplayer.data.repository.PlayerRepository
import com.example.musicplayer.data.Song
import com.example.musicplayer.data.toFavoritesSong
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class RepeatMode {
    NONE,
    ALL,
    ONE
}

@HiltViewModel
class FullPlayerViewModel @Inject constructor(
    private val playerRepository: PlayerRepository,
    private val favoriteDAO: FavoriteDAO
) : ViewModel() {
    val currentSong = playerRepository.currentSong
    val isPlaying = playerRepository.isPlaying
    val progress = playerRepository.progress
    val currentTime = playerRepository.currentTime
    val totalTime = playerRepository.totalTime
    val artUri = playerRepository.artUri
    val songTitle = playerRepository.songTitle
    val artist = playerRepository.artist
    val volume = playerRepository.volume

    fun updateVolume(newVolume: Float) {
        playerRepository.updateVolume(newVolume)
    }

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite = _isFavorite.asStateFlow()

    private val _isShuffleEnabled = MutableStateFlow(false)
    val isShuffleEnabled = _isShuffleEnabled.asStateFlow()

    private val _repeatMode = MutableStateFlow(RepeatMode.NONE)
    val repeatMode = _repeatMode.asStateFlow()

    fun updateSong(song: Song) {
        playerRepository.updateSong(song)
        viewModelScope.launch {
            val isFavoriteSong = favoriteDAO.getFavoriteBySongId(song.id)
            _isFavorite.value = isFavoriteSong != null
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
                // Add the song to the favorites list
                favoriteDAO.insertFavorite(song.toFavoritesSong(System.currentTimeMillis()))
                _isFavorite.value = true
            } else {
                // Remove the song from the favorites list
                favoriteDAO.deleteFavorite(favorite)
                _isFavorite.value = false
            }
        }
    }

    fun updateProgress(newProgress: Float) {
        playerRepository.updateProgress(newProgress)
    }

    fun toggleShuffle() {
        _isShuffleEnabled.value = !_isShuffleEnabled.value
    }

    fun toggleRepeat() {
        _repeatMode.value = when (_repeatMode.value) {
            RepeatMode.NONE -> RepeatMode.ALL
            RepeatMode.ALL -> RepeatMode.ONE
            RepeatMode.ONE -> RepeatMode.NONE
        }
    }

    fun playPrevious() {
        // Implement previous logic
    }

    fun playNext() {
        // Implement next logic
    }
}