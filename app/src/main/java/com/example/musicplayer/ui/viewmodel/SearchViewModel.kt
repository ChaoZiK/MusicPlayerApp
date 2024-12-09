package com.example.musicplayer.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.backend.AudioFetcher
import com.example.musicplayer.data.Song
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val audioFetcher: AudioFetcher
) : ViewModel() {

    private val _isSearchActive = MutableStateFlow(false)
    val isSearchActive: StateFlow<Boolean> = _isSearchActive

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText

    private val _songs = MutableStateFlow<List<Song>>(emptyList())
    val songs: StateFlow<List<Song>> = _songs

    private val _filteredSongs = MutableStateFlow<List<Song>>(emptyList())
    val filteredSongs: StateFlow<List<Song>> = _filteredSongs

    init {
        fetchSongs()
    }

    private fun fetchSongs() {
        viewModelScope.launch {
            val fetchedSongs = audioFetcher.fetchAudioFiles()
            _songs.value = fetchedSongs
            _filteredSongs.value = fetchedSongs
        }
    }

    fun activateSearch() {
        _isSearchActive.value = true
    }

    fun deactivateSearch() {
        _isSearchActive.value = false
    }

    fun onSearchTextChange(newText: String) {
        _searchText.update { newText }
        filterSongs(newText)
    }

    private fun filterSongs(query: String) {
        _filteredSongs.value = if (query.isBlank()) {
            _songs.value
        } else {
            _songs.value.filter { it.title.contains(query, ignoreCase = true) }
        }
    }
}
