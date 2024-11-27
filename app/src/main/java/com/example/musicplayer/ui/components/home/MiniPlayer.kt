package com.example.musicplayer.ui.components.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.rounded.MusicNote
import androidx.compose.ui.draw.clip
import com.example.musicplayer.ui.theme.AppIcons
import com.example.musicplayer.ui.theme.AppShapes

@Composable
fun MiniPlayer(
    modifier: Modifier = Modifier,
    onExpandClick: () -> Unit = {}
) {
    var isPlaying by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(64.dp)
            .clip(AppShapes.large)
            .clickable(onClick = onExpandClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        shape = AppShapes.large
    ) {
        MiniPlayerContent(
            isPlaying = isPlaying,
            onPlayPauseClick = { isPlaying = !isPlaying }
        )
    }
}

@Composable
private fun MiniPlayerContent(
    isPlaying: Boolean,
    onPlayPauseClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SongInfo(modifier = Modifier.weight(1f))
        PlayPauseButton(
            isPlaying = isPlaying,
            onClick = onPlayPauseClick
        )
    }
}

@Composable
private fun SongInfo(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        MusicIcon()
        Spacer(modifier = Modifier.width(12.dp))
        SongDetails()
    }
}

@Composable
private fun MusicIcon() {
    Box(
        modifier = Modifier
            .size(44.dp)
            .background(
                color = Color.White.copy(alpha = 0.2f),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            Icons.Rounded.MusicNote,
            contentDescription = null,
            tint = Color.White.copy(alpha = 0.7f),
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
private fun SongDetails() {
    Column {
        Text(
            "Song Title",
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            "Artist Name",
            color = Color.White.copy(alpha = 0.7f),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun PlayPauseButton(
    isPlaying: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(44.dp)
            .clip(CircleShape)
            .background(
                color = Color.White.copy(alpha = 0.2f),
                shape = CircleShape
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(
                id = if (isPlaying) AppIcons.pause else AppIcons.play
            ),
            contentDescription = if (isPlaying) "Pause" else "Play",
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.size(18.dp)
        )
    }
}