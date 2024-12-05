package com.example.musicplayer.ui.components.sheets

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.musicplayer.data.Song
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.musicplayer.ui.components.shared.BaseBottomSheet
import com.example.musicplayer.ui.theme.AppIcons

@Composable
fun SongOptionsSheet(
    song: Song,
    onDismiss: () -> Unit,
    onInfoClick: () -> Unit
) {
    BaseBottomSheet(
        title = song.title,
        subtitle = song.artist,
        trailingContent = {
            IconButton(onClick = onInfoClick) {
                Icon(
                    painter = painterResource(AppIcons.info),
                    contentDescription = "Song Info",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        onDismiss = onDismiss
    ) {
        Column(modifier = Modifier.padding(vertical = 8.dp)) {
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

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        )

        Column(modifier = Modifier.padding(vertical = 8.dp)) {
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

            ListItem(
                headlineContent = {
                    Box(modifier = Modifier.padding(horizontal = 20.dp)) {
                        Text("Set as ringtone")
                    }
                },
                leadingContent = {
                    Box(modifier = Modifier.padding(start = 20.dp)) {
                        Icon(
                            painter = painterResource(AppIcons.bell),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                modifier = Modifier.clickable { onDismiss() }
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        )

        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            ListItem(
                headlineContent = {
                    Box(modifier = Modifier.padding(horizontal = 20.dp)) {
                        Text("Delete from device")
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
}