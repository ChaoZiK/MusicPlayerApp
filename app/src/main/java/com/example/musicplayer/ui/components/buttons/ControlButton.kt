package com.example.musicplayer.ui.components.buttons

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.example.musicplayer.ui.theme.AppShapes
import com.example.musicplayer.ui.theme.Dimensions

@Composable
fun ControlButton(
    onClick: () -> Unit,
    icon: Painter,
    text: String,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = modifier
            .height(44.dp),
        shape = AppShapes.large
    ) {
        Icon(
            painter = icon,
            contentDescription = text,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(Dimensions.paddingSmall))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}