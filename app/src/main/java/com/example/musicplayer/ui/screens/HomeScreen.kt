package com.example.musicplayer.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musicplayer.data.Song
import com.example.musicplayer.ui.components.home.*
import com.example.musicplayer.ui.theme.AppIcons
import com.example.musicplayer.ui.theme.Dimensions

@Composable
fun HomeScreen(
    onSearchClick: () -> Unit
) {
    val songs = listOf(
        Song("Song 1", "unknown"),
        Song("Song 2", "unknown"),
        Song("Song 3", "unknown"),
        Song("Song 4", "unknown"),
        Song("Song 5", "unknown"),
        Song("Song 6", "unknown"),
        Song("Song 7", "unknown"),
        Song("Song 8", "unknown"),
        Song("Song 9", "unknown"),
        Song("Song 10", "unknown"),
    )

    Scaffold(
        topBar = { TopBar(onSearchClick = onSearchClick) }
    ) { padding ->
        HomeContent(
            padding = padding,
            songs = songs
        )
    }
}

@Composable
private fun HomeContent(
    padding: PaddingValues,
    songs: List<Song>
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Header(songCount = songs.size)

            ControlButtons(
                onShuffleClick = { },
                onPlayClick = { }
            )

            SongList(
                songs = songs,
                onSongClick = { },
                onMoreClick = { }
            )
        }

        MiniPlayer(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = Dimensions.paddingMedium)
        )
    }
}

@Composable
private fun Header(songCount: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimensions.paddingMedium),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        HeaderInfo(songCount = songCount)
        SortButton(onClick = { })
    }
}

@Composable
private fun HeaderInfo(songCount: Int) {
    Text(
        text = "$songCount Songs",
        style = MaterialTheme.typography.titleLarge.copy(
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
        ),
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
private fun SortButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(AppIcons.sort),
            contentDescription = "Sort",
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.size(30.dp)
        )
    }
}