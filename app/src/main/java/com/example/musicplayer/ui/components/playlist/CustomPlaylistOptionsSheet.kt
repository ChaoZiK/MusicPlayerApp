package com.example.musicplayer.ui.components.playlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MusicNote
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.musicplayer.data.Playlist
import com.example.musicplayer.ui.components.shared.BaseBottomSheet
import com.example.musicplayer.ui.theme.AppIcons
import com.example.musicplayer.ui.theme.AppShapes

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
            headlineContent = { Text("Rename") },
            leadingContent = {
                Icon(
                    painter = painterResource(AppIcons.edit),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            },
            modifier = Modifier.clickable { onDismiss() }
        )

        ListItem(
            headlineContent = { Text("Change cover") },
            leadingContent = {
                Icon(
                    painter = painterResource(AppIcons.image),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            },
            modifier = Modifier.clickable { onDismiss() }
        )

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        )
        ListItem(
            headlineContent = { Text("Delete") },
            leadingContent = {
                Icon(
                    painter = painterResource(AppIcons.trash),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            },
            modifier = Modifier.clickable { onDismiss() }
        )
    }
}