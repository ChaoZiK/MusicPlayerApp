package com.example.musicplayer.ui.components.shared

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun OptionItem(
    text: String,
    icon: Int,
    onClick: () -> Unit
) {
    ListItem(
        headlineContent = {
            Box(modifier = Modifier.padding(horizontal = 20.dp)) {
                Text(text)
            }
        },
        leadingContent = {
            Box(modifier = Modifier.padding(start = 20.dp)) {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        modifier = Modifier.clickable(onClick = onClick)
    )
}