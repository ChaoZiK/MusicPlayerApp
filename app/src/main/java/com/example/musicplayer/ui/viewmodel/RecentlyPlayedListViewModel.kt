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
    val recentlyPlayedSongs: LiveData<List<RecentlyPlayedSong>> =
        recentlyPlayedDAO.getAllRecentlyPlayed()

    suspend fun addRecentlyPlayed(song: RecentlyPlayedSong) {
        val existingSong = recentlyPlayedDAO.getRecentlyPlayedBySongId(song.songId)
        if (existingSong != null) {
            val updatedSong = existingSong.copy(lastPlayedTimestamp = System.currentTimeMillis())
            recentlyPlayedDAO.updateRecentlyPlayed(updatedSong)
        } else {
            recentlyPlayedDAO.insertRecentlyPlayed(song)
        }
        recentlyPlayedDAO.trimRecentlyPlayed()
    }

    fun updateRecentlyPlayedSongs(updatedSongs: List<RecentlyPlayedSong>) {
        viewModelScope.launch {
            recentlyPlayedDAO.deleteAllRecentlyPlayed()
            updatedSongs.forEach { recentlyPlayedDAO.insertRecentlyPlayed(it) }
        }
    }
}
