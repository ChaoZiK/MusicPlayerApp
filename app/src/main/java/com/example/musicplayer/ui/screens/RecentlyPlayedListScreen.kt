package com.example.musicplayer.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.musicplayer.data.RecentlyPlayedSong
import com.example.musicplayer.data.toSong
import com.example.musicplayer.ui.components.shared.SongsContentLayout
import com.example.musicplayer.ui.viewmodel.RecentlyPlayedViewModel

@Composable
fun RecentlyPlayedListScreen(
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    onSongClick: (RecentlyPlayedSong) -> Unit,
    viewModel: RecentlyPlayedViewModel = hiltViewModel()
) {
    val recentlyPlayedSongs by viewModel.recentlyPlayedSongs.observeAsState(emptyList())

    SongsContentLayout(
        songs = recentlyPlayedSongs.map { it.toSong() },
        showTopBar = true,
        onBackPressed = onBackClick,
        onSearchClick = onSearchClick,
        onSongClick = { song ->
            recentlyPlayedSongs.find { it.songId == song.id }?.let(onSongClick)
        },
    )
}
