package com.example.musicplayer.ui.components.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun ProgressSlider(
    progress: Float,
    onProgressChange: (Float) -> Unit,
    onProgressComplete: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        val screenWidth = with(LocalDensity.current) {
            LocalConfiguration.current.screenWidthDp.dp
        }
        val totalPadding = (20.dp + 20.dp + 28.dp + 28.dp)
        val thumbSize = 12.dp
        val trackWidth = screenWidth - totalPadding
        val clampedProgress = progress.coerceIn(0f, 1f)

        val halfThumbSize = thumbSize / 2
        val thumbCenterPosition = trackWidth * clampedProgress

        //Track Line
        Box(
            modifier = Modifier
                .width(trackWidth)
                .height(2.dp)
                .background(
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(2.dp)
                )
        )

        //Progress Line
        Box(
            modifier = Modifier
                .width(thumbCenterPosition)
                .height(2.dp)
                .background(
                    color = MaterialTheme.colorScheme.onPrimary,
                    shape = RoundedCornerShape(2.dp)
                )
        )

        //Thumb
        Box(
            modifier = Modifier
                .offset(x = thumbCenterPosition - halfThumbSize)
                .size(thumbSize)
                .background(MaterialTheme.colorScheme.onPrimary, CircleShape)
        )

        Slider(
            value = clampedProgress,
            onValueChange = { newValue ->
                val limitedValue = newValue.coerceIn(0f, 1f)
                onProgressChange(limitedValue)
                if (limitedValue >= 1.0f) {
                    onProgressComplete()
                }
            },
            modifier = Modifier
                .width(trackWidth)
                .alpha(0f),
            colors = SliderDefaults.colors(
                thumbColor = Color.Transparent,
                activeTrackColor = Color.Transparent,
                inactiveTrackColor = Color.Transparent
            )
        )
    }
}