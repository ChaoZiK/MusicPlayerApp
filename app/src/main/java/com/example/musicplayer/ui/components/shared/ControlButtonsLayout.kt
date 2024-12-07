package com.example.musicplayer.ui.components.shared

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.musicplayer.ui.components.buttons.ControlButton
import com.example.musicplayer.ui.theme.AppIcons
import com.example.musicplayer.ui.theme.Dimensions

@Composable
fun ControlButtonsLayout(
    onShuffleClick: () -> Unit,
    onPlayClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = Dimensions.paddingXLarge,
                vertical = Dimensions.paddingSmall
            ),
        horizontalArrangement = Arrangement.spacedBy(Dimensions.paddingXLarge)
    ) {
        ControlButton(
            onClick = onShuffleClick,
            icon = painterResource(AppIcons.shuffle),
            text = "Shuffle",
            modifier = Modifier.weight(1f),
        )

        ControlButton(
            onClick = onPlayClick,
            icon = painterResource(AppIcons.playFilled),
            text = "Play",
            modifier = Modifier.weight(1f),
            iconModifier = Modifier.size(20.dp)
        )
    }
}