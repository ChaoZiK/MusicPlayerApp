package com.example.musicplayer.ui.components.playlist

import androidx.compose.runtime.Composable
import com.example.musicplayer.ui.components.shared.HeaderActionType
import com.example.musicplayer.ui.components.shared.ListHeader

@Composable
fun PlaylistHeader(
    defaultPlaylistsCount: Int,
    customPlaylistsCount: Int,
    onAddClick: () -> Unit
) {
    ListHeader(
        count = defaultPlaylistsCount + customPlaylistsCount,
        title = "Playlists",
        actionType = HeaderActionType.ADD,
        onActionClick = onAddClick
    )
}