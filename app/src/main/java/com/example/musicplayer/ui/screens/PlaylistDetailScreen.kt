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

@Composable
fun PlaylistDetailScreen(
    playlist: Playlist,
    onBackPressed: () -> Unit,
    onSearchClick: () -> Unit,
    onSongClick: (Song) -> Unit,
    onSortSelected: (SortOption, SortDirection) -> Unit
) {
  val context = LocalContext.current
  val musicController = remember { MusicController(context) } // Khởi tạo MusicController

  SongsContentLayout(
    songs = playlist.songs,
    showTopBar = true,
    topBarTitle = playlist.title,
    onBackPressed = onBackPressed,
    onSearchClick = onSearchClick,
    onShuffleClick = { /* Logic xáo trộn bài hát */ },
    onPlayClick = {
      // Logic phát toàn bộ danh sách
      playlist.songs.forEach { song ->
        musicController.playSong(song)
      }
    },
    onSongClick = { song ->
      // Logic phát bài hát cụ thể
      musicController.playSong(song)
    },
    onSortSelected = { option, direction ->
      // Logic sắp xếp
    }
  )
}

