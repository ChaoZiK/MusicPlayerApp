package com.example.musicplayer.ui.components.playlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.musicplayer.data.Playlist
import com.example.musicplayer.ui.components.shared.ListItemContent
import com.example.musicplayer.ui.components.shared.PlaylistIconCard

@Composable
fun PlaylistItem(
    playlist: Playlist,
    modifier: Modifier = Modifier,
    onPlaylistClick: (Playlist) -> Unit = {},
    onMoreClick: (Playlist) -> Unit = {}
) {
    Box(modifier = modifier.clickable { onPlaylistClick(playlist) }) {
        ListItemContent(
            title = playlist.title,
            subtitle = "${playlist.songCount} songs",
            onMoreClick = { onMoreClick(playlist) },
            modifier = Modifier.padding(horizontal = 16.dp),
            leftContent = {
                PlaylistIconCard(playlist = playlist)
            }
        )
    }
}
