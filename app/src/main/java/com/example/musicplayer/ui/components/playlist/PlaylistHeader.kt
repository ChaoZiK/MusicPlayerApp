package com.example.musicplayer.ui.components.playlist

import androidx.compose.runtime.Composable
import com.example.musicplayer.ui.components.shared.BaseHeader
import com.example.musicplayer.ui.components.shared.HeaderActionType

@Composable
fun PlaylistHeader(
    defaultPlaylistsCount: Int,
    customPlaylistsCount: Int,
    onAddClick: () -> Unit
) {
    BaseHeader(
        count = defaultPlaylistsCount + customPlaylistsCount,
        title = "Playlists",
        actionType = HeaderActionType.ADD,
        onActionClick = onAddClick
    )
}