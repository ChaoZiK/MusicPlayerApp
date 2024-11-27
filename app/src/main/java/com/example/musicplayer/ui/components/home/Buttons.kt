package com.example.musicplayer.ui.components.home
import androidx.compose.foundation.layout.*

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.compose.ui.res.painterResource
import com.example.musicplayer.ui.theme.AppShapes
import com.example.musicplayer.ui.theme.Dimensions
import com.example.musicplayer.ui.theme.AppIcons

@Composable
fun Buttons(
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
        Button(
            onClick = onShuffleClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier
                .weight(1f)
                .height(44.dp),
            shape = AppShapes.large
        ) {
            Icon(
                painter = painterResource(AppIcons.shuffle),
                contentDescription = "Shuffle",
                modifier = Modifier.size(Dimensions.iconSizeSmall)
            )
            Spacer(modifier = Modifier.width(Dimensions.paddingSmall))
            Text(
                text = "Trộn",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Button(
            onClick = onPlayClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier
                .weight(1f)
                .height(44.dp),
            shape = AppShapes.large
        ) {
            Icon(
                painter = painterResource(AppIcons.play),
                contentDescription = "Play",
                modifier = Modifier.size(Dimensions.iconSizeSmall)
            )
            Spacer(modifier = Modifier.width(Dimensions.paddingSmall))
            Text(
                text = "Phát",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}