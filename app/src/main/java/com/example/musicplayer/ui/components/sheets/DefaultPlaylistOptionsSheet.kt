package com.example.musicplayer.ui.components.sheets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.musicplayer.data.Playlist
import com.example.musicplayer.ui.components.shared.BaseBottomSheet
import com.example.musicplayer.ui.theme.AppIcons

@Composable
fun DefaultPlaylistOptionsSheet(
    playlist: Playlist,
    onDismiss: () -> Unit
) {
    BaseBottomSheet(
        title = playlist.title,
        subtitle = "${playlist.songCount} songs",
        onDismiss = onDismiss
    ) {
        ListItem(
            headlineContent = {
                Box(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Text("Play")
                }
            },
            leadingContent = {
                Box(modifier = Modifier.padding(start = 20.dp)) {
                    Icon(
                        painter = painterResource(AppIcons.play),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            },
            modifier = Modifier.clickable { onDismiss() }
        )

        ListItem(
            headlineContent = {
                Box(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Text("Play next")
                }
            },
            leadingContent = {
                Box(modifier = Modifier.padding(start = 20.dp)) {
                    Icon(
                        painter = painterResource(AppIcons.nextSong),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            },
            modifier = Modifier.clickable { onDismiss() }
        )

        ListItem(
            headlineContent = {
                Box(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Text("Add to queue")
                }
            },
            leadingContent = {
                Box(modifier = Modifier.padding(start = 20.dp)) {
                    Icon(
                        painter = painterResource(AppIcons.addToQueue),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            },
            modifier = Modifier.clickable { onDismiss() }
        )

        ListItem(
            headlineContent = {
                Box(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Text("Add to playlist")
                }
            },
            leadingContent = {
                Box(modifier = Modifier.padding(start = 20.dp)) {
                    Icon(
                        painter = painterResource(AppIcons.addToPlaylist),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            },
            modifier = Modifier.clickable { onDismiss() }
        )
    }
}
