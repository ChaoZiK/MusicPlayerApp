package com.example.musicplayer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.musicplayer.ui.components.shared.MiniPlayer
import com.example.musicplayer.ui.theme.Dimensions

@Composable
fun MusicPlayerRoot() {
    Box(modifier = Modifier.fillMaxSize()) {
        MusicPlayerNavigation(
            miniPlayerContent = { showMiniPlayer ->
                if (showMiniPlayer) {
                    MiniPlayer(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = Dimensions.paddingXLarge)
                    )
                }
            }
        )
    }
}