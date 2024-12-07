package com.example.musicplayer.ui.components.buttons

import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun MediaControlButton(
    icon: Int,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    alpha: Float = 1f
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .size(40.dp)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = contentDescription,
            tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = alpha),
            modifier = Modifier.size(24.dp)
        )
    }
}