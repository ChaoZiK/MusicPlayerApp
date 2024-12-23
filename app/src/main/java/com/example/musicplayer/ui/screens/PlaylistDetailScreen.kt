package com.example.musicplayer.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.musicplayer.backend.MusicController
import com.example.musicplayer.data.Playlist
import com.example.musicplayer.data.Song
import com.example.musicplayer.data.SortDirection
import com.example.musicplayer.data.SortOption
import com.example.musicplayer.ui.components.shared.SongsContentLayout
import com.example.musicplayer.ui.viewmodel.MiniPlayerViewModel

@Composable
fun PlaylistDetailScreen(
    playlist: Playlist,
    onBackPressed: () -> Unit,
    onSearchClick: () -> Unit,
    onSongClick: (Song) -> Unit,
    onSortSelected: (SortOption, SortDirection) -> Unit,
    miniPlayerViewModel: MiniPlayerViewModel
) {
  val context = LocalContext.current
  val musicController = remember {
    MusicController(context) { song ->
      miniPlayerViewModel.updateSong(song) // Update the mini-player when a song is played
    }
  }

  SongsContentLayout(
    songs = playlist.songs,
    showTopBar = true,
    topBarTitle = playlist.title,
    onBackPressed = onBackPressed,
    onSearchClick = onSearchClick,
    onShuffleClick = {
      musicController.shuffleAndPlay(playlist.songs)
    },
    onPlayClick = {
      playlist.songs.firstOrNull()?.let { song ->
        musicController.playSong(song)
      }
    },
    onSongClick = { song ->
      musicController.playSong(song)
    },
    onSortSelected = { option, direction ->
      // Logic sắp xếp
    },
    miniPlayerViewModel = miniPlayerViewModel
  )
}

