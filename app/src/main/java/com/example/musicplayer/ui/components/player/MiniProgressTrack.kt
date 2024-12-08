package com.example.musicplayer.ui.components.player

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.musicplayer.ui.animations.animateProgress

@Composable
fun MiniProgressTrack(
    progress: Float, modifier: Modifier = Modifier
) {
    val animatedProgress by animateProgress(
        targetValue = progress, durationMillis = 100, easing = LinearEasing
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(2.dp)
            .background(
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.3f),
                shape = RoundedCornerShape(2.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(animatedProgress)
                .height(2.dp)
                .background(
                    color = MaterialTheme.colorScheme.onPrimary, shape = RoundedCornerShape(2.dp)
                )
        )
    }
}
