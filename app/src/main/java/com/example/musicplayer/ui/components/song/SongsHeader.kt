package com.example.musicplayer.ui.components.song

import androidx.compose.runtime.Composable
import com.example.musicplayer.ui.components.shared.HeaderActionType
import com.example.musicplayer.ui.components.shared.ListHeader

@Composable
fun SongsHeader(
    songCount: Int,
    onSortClick: () -> Unit
) {
    ListHeader(
        count = songCount,
        title = "Songs",
        actionType = HeaderActionType.SORT,
        onActionClick = onSortClick
    )
}


