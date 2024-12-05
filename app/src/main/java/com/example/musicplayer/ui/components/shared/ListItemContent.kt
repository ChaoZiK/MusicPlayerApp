package com.example.musicplayer.ui.components.shared

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MusicNote
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.musicplayer.data.Playlist
import com.example.musicplayer.data.PlaylistType
import com.example.musicplayer.ui.theme.AppIcons
import com.example.musicplayer.ui.theme.AppShapes
import com.example.musicplayer.ui.theme.Dimensions
import coil.compose.AsyncImage

@Composable
fun PlaylistIconCard(
    playlist: Playlist,
    modifier: Modifier = Modifier,
    size: Int = 48
) {
    Card(
        modifier = modifier.size(size.dp),
        shape = AppShapes.small,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (playlist.type) {
                PlaylistType.FAVOURITE -> Icon(
                    painter = painterResource(AppIcons.favouriteFilled),
                    contentDescription = null,
                    tint = Color.Red
                )
                PlaylistType.RECENT -> Icon(
                    painter = painterResource(AppIcons.history),
                    contentDescription = null,
                    tint = Color.Yellow
                )
                PlaylistType.CUSTOM -> {
                    if (playlist.coverImage != null) {
                        AsyncImage(
                            model = playlist.coverImage,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        MusicIconCard(modifier = modifier, size = size)
                    }
                }
            }
        }
    }
}

@Composable
private fun MusicIconCard(
    modifier: Modifier = Modifier,
    size: Int = 48
) {
    Card(
        modifier = modifier.size(size.dp),
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
}

@Composable
private fun TitleSubtitleContent(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun ListItemContent(
    title: String,
    subtitle: String,
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier,
    leftContent: @Composable () -> Unit = { MusicIconCard() }
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        leftContent()

        TitleSubtitleContent(
            title = title,
            subtitle = subtitle,
            modifier = Modifier
                .weight(1f)
                .padding(start = Dimensions.paddingMedium)
        )

        IconButton(onClick = onMoreClick) {
            Icon(
                painter = painterResource(AppIcons.moreDots),
                contentDescription = "More options",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}