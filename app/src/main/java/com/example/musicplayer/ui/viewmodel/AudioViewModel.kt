package com.example.musicplayer.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.musicplayer.backend.AudioFetcher
import com.example.musicplayer.backend.sortSongs
import com.example.musicplayer.data.AlphabeticDirection
import com.example.musicplayer.data.Song
import com.example.musicplayer.data.SortDirection
import com.example.musicplayer.data.SortOption
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AudioViewModel(application: Application) : AndroidViewModel(application) {
    private val _songs = MutableLiveData<List<Song>>()
    val songs: LiveData<List<Song>> get() = _songs

    private val audioFetcher = AudioFetcher(application)

    private var currentSortOption: SortOption = SortOption.SONG_NAME
    private var currentSortDirection: SortDirection = AlphabeticDirection.AToZ

    fun fetchSongs() {
        CoroutineScope(Dispatchers.IO).launch {
            val fetchedSongs = audioFetcher.fetchAudioFiles()
            _songs.postValue(fetchedSongs)
        }
    }

    fun updateSongs(fetchedSongs: List<Song>) {
        val sortedSongs = sortSongs(fetchedSongs, currentSortOption, currentSortDirection)
        _songs.postValue(sortedSongs)
    }

    fun updateSort(option: SortOption, direction: SortDirection) {
        currentSortOption = option
        currentSortDirection = direction

        // Reapply sorting to the current song list
        _songs.value?.let { unsortedSongs ->
            val sortedSongs = sortSongs(unsortedSongs, option, direction)
            _songs.postValue(sortedSongs)
        }
    }
}