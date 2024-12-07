package com.example.musicplayer.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.musicplayer.data.repository.PlayerRepository
import com.example.musicplayer.data.Song
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MiniPlayerViewModel @Inject constructor(
    private val playerRepository: PlayerRepository
) : ViewModel() {
    val currentSong = playerRepository.currentSong
    val isPlaying = playerRepository.isPlaying
    val artUri = playerRepository.artUri
    val songTitle = playerRepository.songTitle
    val artist = playerRepository.artist
    val progress = playerRepository.progress

    fun updateSong(song: Song) {
        playerRepository.updateSong(song)
    }

    fun togglePlayPause() {
        playerRepository.togglePlayPause()
    }
}