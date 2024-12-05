package com.example.musicplayer.ui.screens

import androidx.compose.runtime.Composable
import com.example.musicplayer.data.Playlist
import com.example.musicplayer.data.Song
import com.example.musicplayer.data.SortDirection
import com.example.musicplayer.data.SortOption
import com.example.musicplayer.ui.components.shared.SongsContentLayout

@Composable
fun PlaylistDetailScreen(
    playlist: Playlist,
    onBackPressed: () -> Unit,
    onSearchClick: () -> Unit,
    onSongClick: (Song) -> Unit,
    onSortSelected: (SortOption, SortDirection) -> Unit
) {
    SongsContentLayout(
        songs = playlist.songs,
        showTopBar = true,
        topBarTitle = playlist.title,
        onBackPressed = onBackPressed,
        onSearchClick = onSearchClick,
        onSongClick = onSongClick,
        onSortSelected = onSortSelected,
        onShuffleClick = { /* Handle shuffle */ },
        onPlayClick = { /* Handle play */ }
    )
}