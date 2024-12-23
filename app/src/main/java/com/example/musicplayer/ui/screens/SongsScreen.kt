package com.example.musicplayer.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.musicplayer.data.Song
import com.example.musicplayer.data.SortDirection
import com.example.musicplayer.data.SortOption
import com.example.musicplayer.ui.components.shared.SongsContentLayout
import com.example.musicplayer.ui.viewmodel.FullPlayerViewModel
import com.example.musicplayer.ui.viewmodel.MiniPlayerViewModel

@Composable
fun SongsScreen(
    songs: List<Song>,
    miniPlayerViewModel: MiniPlayerViewModel,
    onSortSelected: (SortOption, SortDirection) -> Unit,
    fullPlayerViewModel: FullPlayerViewModel = hiltViewModel()
) {

    LaunchedEffect(songs) {
        fullPlayerViewModel.updatePlaylist(songs)
    }
    SongsContentLayout(
        songs = songs,
        onSortSelected = onSortSelected,
        miniPlayerViewModel = miniPlayerViewModel,
        onSongClick = { song ->
            val index = songs.indexOf(song)
            if (index != -1) {
                fullPlayerViewModel.playSongByIndex(index)
            }
        }
    )
}

