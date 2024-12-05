package com.example.musicplayer.ui.screens

import androidx.compose.runtime.Composable
import com.example.musicplayer.data.Song
import com.example.musicplayer.data.SortDirection
import com.example.musicplayer.data.SortOption
import com.example.musicplayer.ui.components.shared.SongsContentLayout

@Composable
fun SongsScreen(
    songs: List<Song>,
    onSongClick: (Song) -> Unit,
    onSortSelected: (SortOption, SortDirection) -> Unit
) {
    SongsContentLayout(
        songs = songs,
        onSongClick = onSongClick,
        onSortSelected = onSortSelected,
        onShuffleClick = { /* Handle shuffle */ },
        onPlayClick = { /* Handle play */ }
    )
}

