package com.example.musicplayer.ui.components.shared

import com.example.musicplayer.ui.components.sheets.SongDetailsSheet
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.musicplayer.backend.MusicController
import com.example.musicplayer.data.Song
import com.example.musicplayer.data.SortDirection
import com.example.musicplayer.data.SortOption
import com.example.musicplayer.ui.components.song.SongList
import com.example.musicplayer.ui.components.sheets.SortSheet
import com.example.musicplayer.ui.components.playlist.PlaylistDetailTopBar
import com.example.musicplayer.ui.components.sheets.SongBottomSheetsManager
import com.example.musicplayer.ui.components.song.SongsHeader
import com.example.musicplayer.ui.viewmodel.MiniPlayerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongsContentLayout(
  songs: List<Song>,
  modifier: Modifier = Modifier,
  showTopBar: Boolean = false,
  topBarTitle: String = "",
  onBackPressed: (() -> Unit)? = null,
  onSearchClick: (() -> Unit)? = null,
  onShuffleClick: ((Song) -> Unit)? = null,
  onSongClick: ((Song) -> Unit)? = null, // Phát bài hát cụ thể
  onPlayClick: (() -> Unit)? = null, // Phát toàn bộ danh sách
  onSortSelected: ((SortOption, SortDirection) -> Unit)? = null,
  miniPlayerViewModel: MiniPlayerViewModel
) {
  // Context để khởi tạo MusicController
  val context = LocalContext.current
  val musicController = remember {
      MusicController(context)
      {song -> miniPlayerViewModel.updateSong(song)}}

  // Trạng thái UI
  var showSortDialog by remember { mutableStateOf(false) }
  var showOptions by remember { mutableStateOf(false) }
  var showDetails by remember { mutableStateOf(false) }
  var selectedSong by remember { mutableStateOf<Song?>(null) }

  Box(modifier = modifier.fillMaxSize()) {
    Column(modifier = Modifier.fillMaxSize()) {
      // Thanh công cụ trên cùng
      if (showTopBar) {
        PlaylistDetailTopBar(
          onBackClick = { onBackPressed?.invoke() },
          playlistTitle = topBarTitle,
          onSearchClick = { onSearchClick?.invoke() }
        )
      }

      // Header: Số lượng bài hát và tùy chọn sắp xếp
      SongsHeader(
        songCount = songs.size,
        onSortClick = { showSortDialog = true }
      )

      // Nội dung danh sách bài hát
      if (songs.isEmpty()) {
        EmptyList(
          modifier = Modifier
            .weight(1f)
            .padding(vertical = 32.dp)
        )
      } else {
        ControlButtonsLayout(
          onShuffleClick = {
            musicController.shuffleAndPlay(songs)
                           },
          onPlayClick = {
            songs.firstOrNull()?.let { song ->
              musicController.playSong(song)
            }
          }
        )

        SongList(
          songs = songs,
          onSongClick = { song ->
            onSongClick?.invoke(song)
          },
          onMoreClick = { song ->
            selectedSong = song
            showOptions = true
          }
        )
      }
    }

    // Bottom sheet quản lý các tùy chọn
    SongBottomSheetsManager(
      selectedSong = selectedSong,
      showOptions = showOptions,
      showDetails = showDetails,
      onOptionsDismiss = {
        showOptions = false
        selectedSong = null
      },
      onDetailsClick = {
        showOptions = false
        showDetails = true
      },
      onDetailsDismiss = {
        showDetails = false
        selectedSong = null
      }
    )

    // Dialog sắp xếp
    if (showSortDialog) {
      ModalBottomSheet(
        onDismissRequest = { showSortDialog = false },
        containerColor = MaterialTheme.colorScheme.surface,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        dragHandle = {
          Box(
            Modifier
              .padding(top = 15.dp, bottom = 5.dp)
              .width(44.dp)
              .height(4.dp)
              .background(
                MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                MaterialTheme.shapes.small
              )
          )
        }
      ) {
        SortSheet(
          onDismiss = { showSortDialog = false },
          onSortOptionSelected = { option, direction ->
            onSortSelected?.invoke(option, direction)
            showSortDialog = false
          }
        )
      }
    }

    // Bottom sheet hiển thị chi tiết bài hát
    if (showDetails && selectedSong != null) {
      ModalBottomSheet(
        onDismissRequest = {
          showDetails = false
          selectedSong = null
        },
        containerColor = MaterialTheme.colorScheme.surface,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
      ) {
        SongDetailsSheet(
          song = selectedSong!!,
          onDismiss = {
            showDetails = false
            selectedSong = null
          }
        )
      }
    }
  }
}
