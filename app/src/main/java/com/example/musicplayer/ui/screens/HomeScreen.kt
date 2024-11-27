package com.example.musicplayer.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.musicplayer.data.Song
import com.example.musicplayer.ui.components.home.MiniPlayer
import com.example.musicplayer.ui.components.home.TopBar

import com.example.musicplayer.ui.components.home.Buttons
import com.example.musicplayer.ui.components.home.HeaderInfo
import com.example.musicplayer.ui.components.home.SongList
import com.example.musicplayer.ui.components.home.SortButton
import com.example.musicplayer.ui.theme.Dimensions


@Composable
fun HomeScreen(
    onSearchClick: () -> Unit
) {
    val songs = listOf(
        Song("Song 1", "unknown"),
        Song("Song 2", "unknown")
    )

    Scaffold(
        topBar = {
            TopBar(onSearchClick = onSearchClick)
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimensions.paddingMedium),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HeaderInfo(songCount = songs.size)
                    SortButton(onClick = { })
                }

                Buttons(
                    onShuffleClick = { },
                    onPlayClick = { }
                )

                SongList(songs = songs)
            }

            Box(
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                MiniPlayer(
                    modifier = Modifier.padding(bottom = Dimensions.paddingMedium)
                )
            }
        }
    }
}
