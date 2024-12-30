package com.example.musicplayer.ui.components.buttons

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.musicplayer.ui.theme.AppIcons

@Composable
fun MoreButton(
    onMoreClick: () -> Unit
) {
    IconButton(onClick = onMoreClick) {
        Icon(
            painter = painterResource(AppIcons.moreDots),
            contentDescription = "More options",
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.size(18.dp)
        )
    }
}