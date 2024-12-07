package com.example.musicplayer.ui.components.sheets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
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
fun CustomPlaylistOptionsSheet(
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
                    Text("Rename")
                }
            },
            leadingContent = {
                Box(modifier = Modifier.padding(start = 20.dp)) {
                    Icon(
                        painter = painterResource(AppIcons.edit),
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
                    Text("Change cover")
                }
            },
            leadingContent = {
                Box(modifier = Modifier.padding(start = 20.dp)) {
                    Icon(
                        painter = painterResource(AppIcons.image),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            },
            modifier = Modifier.clickable { onDismiss() }
        )

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        )

        ListItem(
            headlineContent = {
                Box(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Text("Delete")
                }
            },
            leadingContent = {
                Box(modifier = Modifier.padding(start = 20.dp)) {
                    Icon(
                        painter = painterResource(AppIcons.trash),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            },
            modifier = Modifier.clickable { onDismiss() }
        )
    }
}