package com.example.musicplayer.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.musicplayer.data.FavoriteSong
import com.example.musicplayer.data.Playlist
import com.example.musicplayer.data.toSong
import com.example.musicplayer.ui.components.shared.SongsContentLayout
import com.example.musicplayer.ui.viewmodel.FavoriteListViewModel

@Composable
fun FavoriteListScreen(
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    onSongClick: (FavoriteSong) -> Unit,
    viewModel: FavoriteListViewModel = hiltViewModel()
) {
    val favoriteSongs by viewModel.favoriteSongs.observeAsState(emptyList())
    val playlist = Playlist(
        id = "favorites",
        title = "Favorites",
        songs = favoriteSongs.map { it.toSong() }
    )

    SongsContentLayout(
        songs = playlist.songs,
        showTopBar = true,
        topBarTitle = playlist.title,
        onBackPressed = onBackClick,
        onSearchClick = onSearchClick,
        onSongClick = { song ->
            favoriteSongs.find { it.songId == song.id }?.let(onSongClick)
        },
        onSortSelected = { _, _ -> /* Handle sort, if needed */ },
        onShuffleClick = { /* Handle shuffle */ },
        onPlayClick = { /* Handle play */ }
    )
}


