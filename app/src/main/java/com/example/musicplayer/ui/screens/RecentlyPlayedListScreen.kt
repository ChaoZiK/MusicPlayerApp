package com.example.musicplayer.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.musicplayer.backend.sortSongs
import com.example.musicplayer.data.RecentlyPlayedSong
import com.example.musicplayer.data.toRecentlyPlayedSong
import com.example.musicplayer.data.toSong
import com.example.musicplayer.ui.components.shared.SongsContentLayout
import com.example.musicplayer.ui.viewmodel.FullPlayerViewModel
import com.example.musicplayer.ui.viewmodel.MiniPlayerViewModel
import com.example.musicplayer.ui.viewmodel.RecentlyPlayedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun RecentlyPlayedListScreen(
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    onSongClick: (RecentlyPlayedSong) -> Unit,
    viewModel: RecentlyPlayedViewModel = hiltViewModel(),
    miniPlayerViewModel: MiniPlayerViewModel,
    fullPlayerViewModel: FullPlayerViewModel,
    coroutineScope: CoroutineScope
) {
    val recentlyPlayedSongs by viewModel.recentlyPlayedSongs.observeAsState(emptyList())
    var sortedSongs by remember { mutableStateOf(recentlyPlayedSongs.map { it.toSong() }) }

    SongsContentLayout(
        songs = recentlyPlayedSongs.map { it.toSong() },
        showTopBar = true,
        onBackPressed = onBackClick,
        onSearchClick = onSearchClick,
        onSongClick = { song ->
            coroutineScope.launch {
                // Update the playlist in PlayerRepository
                fullPlayerViewModel.updatePlaylist(recentlyPlayedSongs.map { it.toSong() })

                // Play the selected song
                val index = recentlyPlayedSongs.indexOfFirst { it.songId == song.id }
                if (index != -1) {
                    fullPlayerViewModel.playSongByIndex(index)
                }
            }
        },
        onSortSelected = { option, direction ->
            val sortedSongs = sortSongs(recentlyPlayedSongs.map { it.toSong() }, option, direction)
            viewModel.updateRecentlyPlayedSongs(sortedSongs.map { it.toRecentlyPlayedSong(System.currentTimeMillis()) })
        },
        onShuffleClick = {
            coroutineScope.launch {
                fullPlayerViewModel.updatePlaylist(recentlyPlayedSongs.map { it.toSong() })
                fullPlayerViewModel.shuffleAndPlay()
            }
        },
        onPlayClick = {
            coroutineScope.launch {
                fullPlayerViewModel.updatePlaylist(recentlyPlayedSongs.map { it.toSong() })
                fullPlayerViewModel.playFirstSong()
            }
        },
        miniPlayerViewModel = miniPlayerViewModel
    )
}
