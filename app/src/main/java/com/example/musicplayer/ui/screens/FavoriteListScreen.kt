package com.example.musicplayer.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.musicplayer.data.FavoriteSong
import com.example.musicplayer.data.Playlist
import com.example.musicplayer.ui.components.playlist.PlaylistItem
import com.example.musicplayer.ui.viewmodel.FavoriteListViewModel

@Composable
fun FavoriteListScreen(
    onSongClick: (FavoriteSong) -> Unit,
    viewModel: FavoriteListViewModel = hiltViewModel()
) {
    val favoriteSongs by viewModel.favoriteSongs.observeAsState(emptyList())

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        items(favoriteSongs) { song ->
            PlaylistItem(
                playlist = Playlist(
                    id = song.songId,
                    title = song.title,
                    songs = listOf()
                ),
                onPlaylistClick = { onSongClick(song) },
                onMoreClick = {}
            )
        }
    }
}


