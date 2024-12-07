package com.example.musicplayer.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.musicplayer.data.repository.PlayerRepository
import com.example.musicplayer.data.Song
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

enum class RepeatMode {
    NONE,
    ALL,
    ONE
}

@HiltViewModel
class FullPlayerViewModel @Inject constructor(
    private val playerRepository: PlayerRepository
) : ViewModel() {
    val currentSong = playerRepository.currentSong
    val isPlaying = playerRepository.isPlaying
    val progress = playerRepository.progress
    val currentTime = playerRepository.currentTime
    val totalTime = playerRepository.totalTime
    val artUri = playerRepository.artUri
    val songTitle = playerRepository.songTitle
    val artist = playerRepository.artist

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite = _isFavorite.asStateFlow()

    private val _isShuffleEnabled = MutableStateFlow(false)
    val isShuffleEnabled = _isShuffleEnabled.asStateFlow()

    private val _repeatMode = MutableStateFlow(RepeatMode.NONE)
    val repeatMode = _repeatMode.asStateFlow()

    fun updateSong(song: Song) {
        playerRepository.updateSong(song)
    }

    fun togglePlayPause() {
        playerRepository.togglePlayPause()
    }

    fun toggleFavorite() {
        _isFavorite.value = !_isFavorite.value
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

    override fun onCleared() {
        super.onCleared()
        playerRepository.stopPlayback()
    }
}