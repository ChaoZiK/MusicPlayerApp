package com.example.musicplayer.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.musicplayer.data.Song
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel() {
    private val _isSearchActive = MutableStateFlow(false)
    val isSearchActive: StateFlow<Boolean> = _isSearchActive

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText

    private val _allSongs = MutableStateFlow<List<Song>>(emptyList())
    private val _searchResults = MutableStateFlow<List<Song>>(emptyList())
    val searchResults: StateFlow<List<Song>> = _searchResults

    fun activateSearch(songs: List<Song>) {
        _isSearchActive.value = true
        _allSongs.value = songs
    }

    fun deactivateSearch() {
        _isSearchActive.value = false
        _searchText.value = ""
        _searchResults.value = emptyList()
    }

    fun onSearchTextChange(newText: String) {
        _searchText.update { newText }
        filterSongs(newText)
    }

    private fun filterSongs(query: String) {
        if (query.isBlank()) {
            _searchResults.value = emptyList()
        } else {
            _searchResults.value = _allSongs.value.filter { song ->
                song.title.contains(query, ignoreCase = true) ||
                        song.artist.contains(query, ignoreCase = true)
            }
        }
    }
}