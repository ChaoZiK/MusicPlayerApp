package com.example.musicplayer.ui.components.sheets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.musicplayer.data.Song
import com.example.musicplayer.ui.components.shared.BaseBottomSheet
import com.example.musicplayer.ui.components.shared.OptionItem
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
            OptionItem(
                text = "Play next",
                icon = AppIcons.nextSong,
                onClick = { }
            )
            OptionItem(
                text = "Add to queue",
                icon = AppIcons.addToQueue,
                onClick = { }
            )
            OptionItem(
                text = "Add to playlist",
                icon = AppIcons.addToPlaylist,
                onClick = { }
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        )

        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            OptionItem(
                text = "Change cover",
                icon = AppIcons.image,
                onClick = { }
            )
            OptionItem(
                text = "Set as ringtone",
                icon = AppIcons.bell,
                onClick = { }
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        )

        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            OptionItem(
                text = "Delete from device",
                icon = AppIcons.trash,
                onClick = { }
            )
        }
    }
}