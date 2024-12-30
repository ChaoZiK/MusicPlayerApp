package com.example.musicplayer.ui.components.sheets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.musicplayer.data.Song
import com.example.musicplayer.ui.components.player.VolumeOptionItem
import com.example.musicplayer.ui.components.shared.BaseBottomSheet
import com.example.musicplayer.ui.components.shared.OptionItem
import com.example.musicplayer.ui.theme.AppIcons
import com.example.musicplayer.ui.viewmodel.FullPlayerViewModel

@Composable
fun FullPlayerOptionsSheet(
    song: Song,
    onDismiss: () -> Unit,
    onInfoClick: () -> Unit,
    viewModel: FullPlayerViewModel,
) {
    val systemVolume by viewModel.volume.collectAsState()

    BaseBottomSheet(
        title = song.title, subtitle = song.artist, trailingContent = {
            IconButton(onClick = onInfoClick) {
                Icon(
                    painter = painterResource(AppIcons.info),
                    contentDescription = "Song Info",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }, onDismiss = onDismiss
    ) {
        VolumeOptionItem(
            volume = systemVolume,
            onVolumeChange = { newVolume  ->
                viewModel.updateSystemVolume(newVolume)
            }
        )
    }
}