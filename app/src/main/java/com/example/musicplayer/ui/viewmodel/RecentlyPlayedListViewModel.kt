package com.example.musicplayer.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.data.RecentlyPlayedDAO
import com.example.musicplayer.data.RecentlyPlayedSong
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecentlyPlayedViewModel @Inject constructor(
    private val recentlyPlayedDAO: RecentlyPlayedDAO
) : ViewModel() {
    // Use LiveData from DAO directly
    val recentlyPlayedSongs: LiveData<List<RecentlyPlayedSong>> = recentlyPlayedDAO.getAllRecentlyPlayed()

    suspend fun addRecentlyPlayed(song: RecentlyPlayedSong) {
        val existingSong = recentlyPlayedDAO.getRecentlyPlayedBySongId(song.songId)
        if (existingSong != null) {
            // Update the timestamp to move it to the top of the list
            val updatedSong = existingSong.copy(lastPlayedTimestamp = System.currentTimeMillis())
            recentlyPlayedDAO.updateRecentlyPlayed(updatedSong)
        } else {
            // Insert as a new entry
            recentlyPlayedDAO.insertRecentlyPlayed(song)
        }
        recentlyPlayedDAO.trimRecentlyPlayed() // Ensure the list size limit
    }

    fun updateRecentlyPlayedSongs(updatedSongs: List<RecentlyPlayedSong>) {
        viewModelScope.launch {
            recentlyPlayedDAO.deleteAllRecentlyPlayed() // Optional: clear all existing recently played entries
            updatedSongs.forEach { recentlyPlayedDAO.insertRecentlyPlayed(it) }
        }
    }
}
