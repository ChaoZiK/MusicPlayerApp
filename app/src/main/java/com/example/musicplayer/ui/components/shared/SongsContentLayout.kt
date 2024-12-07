package com.example.musicplayer.ui.components.shared

import com.example.musicplayer.ui.components.sheets.SongDetailsSheet
import androidx.compose.foundation.background
import android.Manifest
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musicplayer.backend.AudioFetcher
import com.example.musicplayer.data.Song
import com.example.musicplayer.data.SortDirection
import com.example.musicplayer.data.SortOption
import com.example.musicplayer.ui.components.song.SongList
import com.example.musicplayer.ui.components.sheets.SortSheet
import com.example.musicplayer.ui.components.playlist.PlaylistDetailTopBar
import com.example.musicplayer.ui.components.sheets.SongBottomSheetsManager
import com.example.musicplayer.ui.components.song.SongsHeader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongsContentLayout(
    songs: List<Song>,
    modifier: Modifier = Modifier,
    showTopBar: Boolean = false,
    topBarTitle: String = "",
    onBackPressed: (() -> Unit)? = null,
    onSearchClick: (() -> Unit)? = null,
    onSongClick: ((Song) -> Unit)? = null,
    onShuffleClick: (() -> Unit)? = null,
    onPlayClick: (() -> Unit)? = null,
    onSortSelected: ((SortOption, SortDirection) -> Unit)? = null,
) {
    var showOptions by remember { mutableStateOf(false) }
    var selectedSong by remember { mutableStateOf<Song?>(null) }
    var showSortDialog by remember { mutableStateOf(false) }
    var showDetails by remember { mutableStateOf(false) }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            if (showTopBar) {
                PlaylistDetailTopBar(
                    onBackClick = { onBackPressed?.invoke() },
                    playlistTitle = topBarTitle,
                    onSearchClick = { onSearchClick?.invoke() }
                )
            }

            SongsHeader(
                songCount = songs.size,
                onSortClick = { showSortDialog = true }
            )

            if (songs.isEmpty()) {
                EmptyList(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 32.dp)
                )
            } else {
                ControlButtonsLayout(
                    onShuffleClick = { onShuffleClick?.invoke() },
                    onPlayClick = { onPlayClick?.invoke() }
                )

                SongList(
                    songs = songs,
                    onSongClick = { song -> onSongClick?.invoke(song) },
                    onMoreClick = { song ->
                        selectedSong = song
                        showOptions = true
                    }
                )
            }
        }

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