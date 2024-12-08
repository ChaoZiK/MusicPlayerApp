package com.example.musicplayer.ui.components.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.musicplayer.ui.viewmodel.RepeatMode

@Composable
fun PlayerControls(
    modifier: Modifier = Modifier,
    isPlaying: Boolean,
    progress: Float = 0f,
    currentTime: String = "00:00",
    totalTime: String = "00:00",
    isShuffleEnabled: Boolean = false,
    repeatMode: RepeatMode = RepeatMode.NONE,
    onProgressChange: (Float) -> Unit = {},
    onPlayPauseClick: () -> Unit,
    onShuffleClick: () -> Unit = {},
    onPreviousClick: () -> Unit = {},
    onNextClick: () -> Unit = {},
    onRepeatClick: () -> Unit = {},
    onProgressComplete: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(24.dp)
            )
            .padding(horizontal = 28.dp, vertical = 36.dp)
    ) {
        Column{
            ProgressSlider(
                progress = progress,
                onProgressChange = onProgressChange,
                onProgressComplete = onProgressComplete
            )

            Spacer(modifier = Modifier.height(8.dp))

            TimeDisplay(
                currentTime = currentTime,
                totalTime = totalTime
            )

            Spacer(modifier = Modifier.height(20.dp))

            MediaControls(
                isPlaying = isPlaying,
                isShuffleEnabled = isShuffleEnabled,
                repeatMode = repeatMode,
                onPlayPauseClick = onPlayPauseClick,
                onShuffleClick = onShuffleClick,
                onPreviousClick = onPreviousClick,
                onNextClick = onNextClick,
                onRepeatClick = onRepeatClick
            )
        }
    }
}
