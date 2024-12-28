package com.example.musicplayer.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.musicplayer.backend.sortSongs
import com.example.musicplayer.data.FavoriteSong
import com.example.musicplayer.data.Playlist
import com.example.musicplayer.data.toFavoriteSong
import com.example.musicplayer.data.toSong
import com.example.musicplayer.ui.components.shared.SongsContentLayout
import com.example.musicplayer.ui.viewmodel.FavoriteListViewModel
import com.example.musicplayer.ui.viewmodel.FullPlayerViewModel
import com.example.musicplayer.ui.viewmodel.MiniPlayerViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun FavoriteListScreen(
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    onSongClick: (FavoriteSong) -> Unit,
    viewModel: FavoriteListViewModel = hiltViewModel(),
    miniPlayerViewModel: MiniPlayerViewModel,
    fullPlayerViewModel: FullPlayerViewModel,
    coroutineScope: CoroutineScope
) {
    val favoriteSongs by viewModel.favoriteSongs.observeAsState(emptyList())
    var sortedSongs by remember { mutableStateOf(favoriteSongs.map { it.toSong() }) }

    SongsContentLayout(
        songs = favoriteSongs.map { it.toSong() },
        showTopBar = true,
        onBackPressed = onBackClick,
        onSearchClick = onSearchClick,
        onSongClick = { song ->
            coroutineScope.launch {
                // Update the playlist in PlayerRepository
                fullPlayerViewModel.updatePlaylist(favoriteSongs.map { it.toSong() })

                // Play the selected song
                val index = favoriteSongs.indexOfFirst { it.songId == song.id }
                if (index != -1) {
                    fullPlayerViewModel.playSongByIndex(index)
                }
            }
        },
        onSortSelected = { option, direction ->
            val sortedSongs = sortSongs(favoriteSongs.map { it.toSong() }, option, direction)
            viewModel.updateFavoriteSongs(sortedSongs.map { it.toFavoriteSong(System.currentTimeMillis()) })
        },
        onShuffleClick = {
            coroutineScope.launch {
                fullPlayerViewModel.updatePlaylist(favoriteSongs.map { it.toSong() })
                fullPlayerViewModel.shuffleAndPlay()
            }
        },
        onPlayClick = {
            coroutineScope.launch {
                fullPlayerViewModel.updatePlaylist(favoriteSongs.map { it.toSong() })
                fullPlayerViewModel.playFirstSong()
            }
        },
        miniPlayerViewModel = miniPlayerViewModel
    )
}


