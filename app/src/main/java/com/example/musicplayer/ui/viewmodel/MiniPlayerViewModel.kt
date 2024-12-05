package com.example.musicplayer.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.musicplayer.data.Song
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MiniPlayerViewModel @Inject constructor() : ViewModel() {
    private val _currentSong = MutableStateFlow<Song?>(null)
    val currentSong = _currentSong.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()

    fun updateSong(song: Song) {
        _currentSong.value = song
    }

    fun togglePlayPause() {
        _isPlaying.value = !_isPlaying.value
    }
}