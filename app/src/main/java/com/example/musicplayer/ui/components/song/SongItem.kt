package com.example.musicplayer.ui.components.song


import androidx.compose.runtime.Composable
import com.example.musicplayer.data.Song
import com.example.musicplayer.ui.components.shared.ListItemContent

@Composable
fun SongItem(
    song: Song,
    onMoreClick: () -> Unit,
) {
    ListItemContent(
        title = song.title,
        subtitle = song.artist,
        onMoreClick = onMoreClick
    )
}