package com.example.musicplayer.ui.components.player

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.musicplayer.ui.components.buttons.MediaControlButton
import com.example.musicplayer.ui.components.buttons.PlayPauseButton
import com.example.musicplayer.ui.theme.AppIcons
import com.example.musicplayer.data.repository.PlayerRepository.RepeatMode

@Composable
fun MediaControls(
    isPlaying: Boolean,
    isShuffleEnabled: Boolean,
    repeatMode: RepeatMode,
    onPlayPauseClick: () -> Unit,
    onShuffleClick: () -> Unit,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    onRepeatClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        MediaControlButton(
            icon = AppIcons.shuffle,
            contentDescription = "Shuffle",
            onClick = onShuffleClick,
            alpha = if (isShuffleEnabled) 1f else 0.7f
        )
        MediaControlButton(
            icon = AppIcons.previous,
            contentDescription = "Previous",
            onClick = onPreviousClick
        )
        PlayPauseButton(
            isPlaying = isPlaying,
            onClick = onPlayPauseClick
        )
        MediaControlButton(
            icon = AppIcons.next,
            contentDescription = "Next",
            onClick = onNextClick
        )
        MediaControlButton(
            icon = when (repeatMode) {
                RepeatMode.ONE -> AppIcons.repeatOnce
                else -> AppIcons.repeat
            },
            contentDescription = when (repeatMode) {
                RepeatMode.NONE -> "Repeat Off"
                RepeatMode.ALL -> "Repeat All"
                RepeatMode.ONE -> "Repeat One"
            },
            onClick = onRepeatClick,
            alpha = if (repeatMode == RepeatMode.NONE) 0.7f else 1f
        )
    }
}