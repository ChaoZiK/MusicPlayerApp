package com.example.musicplayer.ui.components.player
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.musicplayer.ui.theme.AppIcons

@Composable
fun VolumeOptionItem(
    volume: Float,
    onVolumeChange: (Float) -> Unit
) {
    ListItem(
        headlineContent = {
            Box(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            ) {
                VolumeSlider(
                    volume = volume,
                    onVolumeChange = onVolumeChange,
                )
            }
        },
        leadingContent = {
            Box(modifier = Modifier.padding(start = 20.dp)) {
                Icon(
                    painter = painterResource(AppIcons.volume),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    )
}

@Composable
private fun VolumeSlider(
    volume: Float,
    onVolumeChange: (Float) -> Unit
) {
    var trackWidth by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    Box(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(20.dp)
            .onGloballyPositioned { coordinates ->
                trackWidth = with(density) { coordinates.size.width.toDp() }
            },
        contentAlignment = Alignment.CenterStart
    ) {
        val thumbSize = 16.dp
        val clampedVolume = volume.coerceIn(0f, 1f)
        val halfThumbSize = thumbSize / 2
        val thumbCenterPosition = trackWidth * clampedVolume

        // Track Line
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(3.dp)
                .background(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(2.dp)
                )
        )

        // Volume Line
        Box(
            modifier = Modifier
                .width(thumbCenterPosition)
                .height(3.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(2.dp)
                )
        )

        // Thumb
        Box(
            modifier = Modifier
                .offset(x = thumbCenterPosition - halfThumbSize)
                .size(thumbSize)
                .background(MaterialTheme.colorScheme.primary, CircleShape)
        )

        // Invisible Slider
        Slider(
            value = clampedVolume,
            onValueChange = { newValue ->
                val limitedValue = newValue.coerceIn(0f, 1f)
                onVolumeChange(limitedValue)
            },
            modifier = Modifier
                .fillMaxWidth()
                .alpha(0f),
            colors = SliderDefaults.colors(
                thumbColor = Color.Transparent,
                activeTrackColor = Color.Transparent,
                inactiveTrackColor = Color.Transparent
            )
        )
    }
}