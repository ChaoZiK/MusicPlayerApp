package com.example.musicplayer.ui.components.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.musicplayer.data.Song
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.rounded.MusicNote
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.musicplayer.ui.theme.AppIcons
import com.example.musicplayer.ui.theme.AppShapes

@Composable
fun SongOptionsSheet(
    song: Song,
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
    ) {
        ListItem(
            headlineContent = { Text(song.title) },
            supportingContent = { Text(song.artist) },
            leadingContent = {
                Card(
                    modifier = Modifier.size(40.dp),
                    shape = AppShapes.small,
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Rounded.MusicNote,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                        )
                    }
                }
            },
            modifier = Modifier.padding(bottom = 8.dp)
        )

        HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))

        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            ListItem(
                headlineContent = { Text("Play next") },
                leadingContent = { Icon(painter = painterResource(AppIcons.nextSong), contentDescription = null, tint = MaterialTheme.colorScheme.onSurface) },
                modifier = Modifier.clickable { onDismiss() }
            )

            ListItem(
                headlineContent = { Text("Add to queue") },
                leadingContent = { Icon(painter = painterResource(AppIcons.addToQueue), contentDescription = null, tint = MaterialTheme.colorScheme.onSurface) },
                modifier = Modifier.clickable { onDismiss() }
            )

            ListItem(
                headlineContent = { Text("Add to playlist") },
                leadingContent = { Icon(painter = painterResource(AppIcons.addToPlaylist), contentDescription = null, tint = MaterialTheme.colorScheme.onSurface) },
                modifier = Modifier.clickable { onDismiss() }
            )
        }

        HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))

        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            ListItem(
                headlineContent = { Text("Change cover") },
                leadingContent = { Icon(painter = painterResource(AppIcons.image), contentDescription = null, tint = MaterialTheme.colorScheme.onSurface) },
                modifier = Modifier.clickable { onDismiss() }
            )

            ListItem(
                headlineContent = { Text("Set as ringtone") },
                leadingContent = { Icon(painter = painterResource(AppIcons.bell), contentDescription = null, tint = MaterialTheme.colorScheme.onSurface) },
                modifier = Modifier.clickable { onDismiss() }
            )
        }

        HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))

        Column(modifier = Modifier.padding(vertical = 8.dp, horizontal = 2.dp)) {
            ListItem(
                headlineContent = { Text("Delete from device") },
                leadingContent = { Icon(painter = painterResource(AppIcons.trash), contentDescription = null, tint = MaterialTheme.colorScheme.onSurface) },
                modifier = Modifier.clickable { onDismiss() }
            )
        }

        Button(
            onClick = { onDismiss() },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 16.dp, vertical = 4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            shape = MaterialTheme.shapes.large
        ) {
            Text("CLOSE")
        }
    }
}