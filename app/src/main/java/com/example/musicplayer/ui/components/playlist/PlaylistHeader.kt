package com.example.musicplayer.ui.components.playlist

import androidx.compose.runtime.Composable
import com.example.musicplayer.ui.components.shared.BaseHeader
import com.example.musicplayer.ui.components.shared.HeaderActionType

@Composable
fun PlaylistHeader(
    defaultPlaylistsCount: Int,
    onAddClick: () -> Unit
) {
    BaseHeader(
        count = defaultPlaylistsCount,
        title = "Playlists",
        actionType = HeaderActionType.TOP,
        onActionClick = onAddClick
    )
}