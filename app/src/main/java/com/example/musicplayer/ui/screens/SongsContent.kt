package com.example.musicplayer.ui.screens

import androidx.compose.foundation.background
import android.Manifest
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import com.example.musicplayer.ui.components.shared.ControlButtons
import com.example.musicplayer.ui.components.shared.MiniPlayer
import com.example.musicplayer.ui.components.song.SongList
import com.example.musicplayer.ui.components.song.SongOptionsSheet
import com.example.musicplayer.ui.components.shared.SortDialog
import com.example.musicplayer.ui.components.song.SongsHeader
import com.example.musicplayer.ui.theme.Dimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongsContent(
    padding: PaddingValues,
    songs: List<Song>
) {

    var showOptions by remember { mutableStateOf(false) }
    var selectedSong by remember { mutableStateOf<Song?>(null) }
    var showSortDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            SongsHeader(
                songCount = songs.size,
                onSortClick = { showSortDialog = true }
            )
            ControlButtons(
                onShuffleClick = { },
                onPlayClick = { }
            )

            SongList(
                songs = songs,
                onSongClick = { },
                onMoreClick = { song ->
                    selectedSong = song
                    showOptions = true
                }
            )
        }

        MiniPlayer(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = Dimensions.paddingXLarge)
        )
        if (showOptions && selectedSong != null) {
            ModalBottomSheet(
                onDismissRequest = {
                    showOptions = false
                    selectedSong = null
                },
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
                SongOptionsSheet(
                    song = selectedSong!!,
                    onDismiss = {
                        showOptions = false
                        selectedSong = null
                    }
                )
            }
        }
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
                SortDialog(
                    onDismiss = { showSortDialog = false },
                    onSortOptionSelected = { option, direction ->
                        // Handle sorting
                        //handleSorting(option, direction)

                        showSortDialog = false
                    }
                )
            }
        }
    }
}
