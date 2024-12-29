package com.example.musicplayer.ui.components.song

import androidx.compose.runtime.Composable
import com.example.musicplayer.ui.components.shared.HeaderActionType
import com.example.musicplayer.ui.components.shared.BaseHeader

@Composable
fun SongsHeader(
    songCount: Int,
    isSearchScreen: Boolean = false,
    onSortClick: () -> Unit
) {
    val title = if (isSearchScreen) "results" else "Songs" // Update title dynamically
    BaseHeader(
        count = songCount,
        title = title,
        actionType = if (isSearchScreen) null else HeaderActionType.SORT, // Hide sort for SearchScreen
        onActionClick = onSortClick
    )
}


