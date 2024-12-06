package com.example.musicplayer.ui.components.cards

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.musicplayer.data.Playlist
import com.example.musicplayer.data.PlaylistType
import com.example.musicplayer.ui.theme.AppIcons
import com.example.musicplayer.ui.theme.AppShapes

@Composable
fun PlaylistIconCard(
    playlist: Playlist,
    modifier: Modifier = Modifier,
    size: Int = 48
) {
    when (playlist.type) {
        PlaylistType.FAVOURITE -> Card(
            modifier = modifier.size(size.dp),
            shape = AppShapes.small,
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFFFE4E4)
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(AppIcons.favouriteFilled),
                    contentDescription = null,
                    tint = Color(0xFFFF4444),
                    modifier = Modifier.size((size * 0.6).dp)
                )
            }
        }

        PlaylistType.RECENT -> Card(
            modifier = modifier.size(size.dp),
            shape = AppShapes.small,
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFFFF8E1) // Light yellow background
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(AppIcons.history),
                    contentDescription = null,
                    tint = Color(0xFFFFB300),
                    modifier = Modifier.size((size * 0.6).dp)
                )
            }
        }

        PlaylistType.CUSTOM -> Card(
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
