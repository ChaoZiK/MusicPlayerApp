package com.example.musicplayer.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.musicplayer.ui.components.buttons.AddToPlaylistButton
import com.example.musicplayer.ui.components.buttons.CurrentPlayingButton
import com.example.musicplayer.ui.components.buttons.DownButton
import com.example.musicplayer.ui.components.buttons.FavoriteButton
import com.example.musicplayer.ui.components.buttons.MoreButton
import com.example.musicplayer.ui.components.buttons.ShareButton
import com.example.musicplayer.ui.components.player.AlbumArt
import com.example.musicplayer.ui.components.player.PlayerControls
import com.example.musicplayer.ui.components.sheets.FullPlayerOptionsSheet
import com.example.musicplayer.ui.components.sheets.SongDetailsSheet
import com.example.musicplayer.ui.viewmodel.FullPlayerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullPlayerScreen(
    onBackClick: () -> Unit,
    viewModel: FullPlayerViewModel = hiltViewModel(),
) {
    val isPlaying by viewModel.isPlaying.collectAsState()
    val currentSong by viewModel.currentSong.collectAsState()
    val progress by viewModel.progress.collectAsState()
    val currentTime by viewModel.currentTime.collectAsState()
    val totalTime by viewModel.totalTime.collectAsState()
    val isFavorite by viewModel.isFavorite.collectAsState()
    val artUri by viewModel.artUri.collectAsState()
    val songTitle by viewModel.songTitle.collectAsState()
    val artist by viewModel.artist.collectAsState()
    val isShuffleEnabled by viewModel.isShuffleEnabled.collectAsState()
    val repeatMode by viewModel.repeatMode.collectAsState()
    var showOptionsSheet by remember { mutableStateOf(false) }
    var showDetails by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .padding(horizontal = 20.dp)
        ) {
            TopBar(
                onBackClick = onBackClick,
                onMoreClick = { showOptionsSheet = true },
                title = currentSong?.title ?: "Playing"
            )

            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                val (albumArt, songInfo, actions, controls) = createRefs()

                AlbumArt(
                    modifier = Modifier.constrainAs(albumArt) {
                        top.linkTo(parent.top, margin = 32.dp)
                        centerHorizontallyTo(parent)
                        width = Dimension.percent(0.85f)
                    },
                    artUri = artUri,
                    isPlaying = isPlaying
                )

                SongInfo(
                    modifier = Modifier.constrainAs(songInfo) {
                        top.linkTo(albumArt.bottom, margin = 32.dp)
                        centerHorizontallyTo(parent)
                    },
                    title = songTitle,
                    artist = artist
                )

                currentSong?.let { song ->
                    Log.d("FullPlayerScreen", "Current Song: ${song.title}")
                }

                ActionButtons(
                    modifier = Modifier.constrainAs(actions) {
                        bottom.linkTo(controls.top, margin = 16.dp)
                        centerHorizontallyTo(parent)
                        width = Dimension.fillToConstraints
                    },
                    isFavorite = isFavorite,
                    onFavoriteClick = {
                        Log.d("FullPlayerScreen", "Favorite button clicked")
                        viewModel.toggleFavorite()
                    }
                )

                PlayerControls(
                    modifier = Modifier.constrainAs(controls) {
                        bottom.linkTo(parent.bottom, margin = 40.dp)
                        centerHorizontallyTo(parent)
                        width = Dimension.fillToConstraints
                    },
                    isPlaying = isPlaying,
                    progress = progress,
                    currentTime = currentTime,
                    totalTime = totalTime,
                    isShuffleEnabled = isShuffleEnabled,
                    repeatMode = repeatMode,
                    onProgressChange = { viewModel.updateProgress(it) },
                    onPlayPauseClick = { viewModel.togglePlayPause() },
                    onShuffleClick = { viewModel.toggleShuffle() },
                    onPreviousClick = { viewModel.playPrevious() },
                    onNextClick = { viewModel.playNext() },
                    onRepeatClick = { viewModel.toggleRepeat() }
                )
            }
        }
        if (showOptionsSheet) {
            ModalBottomSheet(
                onDismissRequest = { showOptionsSheet = false },
                containerColor = MaterialTheme.colorScheme.surface,
                sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
            ) {
                currentSong?.let {
                    FullPlayerOptionsSheet(
                        song = it,
                        onDismiss = { showOptionsSheet = false },
                        onInfoClick = {
                            showOptionsSheet = false
                            showDetails = true
                        },
                        viewModel = viewModel
                    )
                }
            }
        }

        if (showDetails) {
            ModalBottomSheet(
                onDismissRequest = { showDetails = false },
                containerColor = MaterialTheme.colorScheme.surface,
                sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
            ) {
                SongDetailsSheet(
                    song = currentSong ?: return@ModalBottomSheet,
                    onDismiss = { showDetails = false }
                )
            }
        }
    }
}

@Composable
private fun TopBar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onMoreClick: () -> Unit,
    title: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        DownButton(onBackClick = onBackClick)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Download",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
        MoreButton(onMoreClick = onMoreClick)
    }
}

@Composable
private fun SongInfo(
    modifier: Modifier = Modifier,
    title: String,
    artist: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = artist,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}

@Composable
private fun ActionButtons(
    modifier: Modifier = Modifier,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        FavoriteButton(
            isFavorite = isFavorite,
            onClick = onFavoriteClick
        )
        AddToPlaylistButton()
        ShareButton()
        CurrentPlayingButton()
    }
}


