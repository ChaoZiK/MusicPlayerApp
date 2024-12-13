package com.example.musicplayer.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.musicplayer.backend.AudioFetcher
import com.example.musicplayer.data.Song
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AudioViewModel(application: Application) : AndroidViewModel(application) {
    private val _songs = MutableLiveData<List<Song>>()
    val songs: LiveData<List<Song>> get() = _songs

    private val audioFetcher = AudioFetcher(application)

    fun fetchSongs() {
        CoroutineScope(Dispatchers.IO).launch {
            val fetchedSongs = audioFetcher.fetchAudioFiles()
            _songs.postValue(fetchedSongs)
        }
    }

    fun updateSongs(fetchedSongs: List<Song>) {
        _songs.postValue(fetchedSongs)
    }
}