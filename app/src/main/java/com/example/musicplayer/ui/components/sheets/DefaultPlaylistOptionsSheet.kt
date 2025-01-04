package com.example.musicplayer.ui.components.sheets

import androidx.compose.runtime.Composable
import com.example.musicplayer.data.Playlist
import com.example.musicplayer.ui.components.shared.BaseBottomSheet
import com.example.musicplayer.ui.components.shared.OptionItem
import com.example.musicplayer.ui.theme.AppIcons

@Composable
fun DefaultPlaylistOptionsSheet(
    playlist: Playlist,
    onDismiss: () -> Unit,
    onPlayClick: () -> Unit
) {
    BaseBottomSheet(
        title = playlist.title,
        subtitle = "${playlist.songCount} songs",
        onDismiss = onDismiss
    ) {
        OptionItem(
            text = "Play",
            icon = AppIcons.play,
            onClick = {
                onPlayClick()
                onDismiss()
            }
        )
    }
}
