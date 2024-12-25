  package com.example.musicplayer.ui.viewmodel

  import androidx.lifecycle.ViewModel
  import androidx.lifecycle.viewModelScope
  import com.example.musicplayer.data.Song
  import com.example.musicplayer.data.repository.PlayerRepository
  import dagger.hilt.android.lifecycle.HiltViewModel
  import kotlinx.coroutines.flow.MutableStateFlow
  import kotlinx.coroutines.flow.asStateFlow
  import kotlinx.coroutines.launch
  import javax.inject.Inject

  @HiltViewModel
  class HomeViewModel @Inject constructor(
      private val playerRepository: PlayerRepository
  ) : ViewModel() {
      private val _songs = MutableStateFlow<List<Song>>(emptyList())
      val songs = _songs.asStateFlow()
      private val _selectedTab = MutableStateFlow(0)
      val selectedTab = _selectedTab.asStateFlow()
      val isPlaying = playerRepository.isPlaying

      fun setSelectedTab(tab: Int) {
          _selectedTab.value = tab
      }

      fun loadSongs() {
          viewModelScope.launch {
          }
      }

      fun toggleSort() {
      }

      fun shuffleAndPlay() {
          playerRepository.stopPlayback()
          playerRepository.shuffle()
          playerRepository.togglePlayPause()
      }

      fun playFirstSong() {
          playerRepository.stopPlayback()
          playerRepository.playFirstSong()
          playerRepository.togglePlayPause()
      }

      fun updatePlaylist(songs: List<Song>) {
          playerRepository.updatePlaylist(songs)
      }
  }
