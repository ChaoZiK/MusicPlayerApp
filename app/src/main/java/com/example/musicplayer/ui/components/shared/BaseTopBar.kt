package com.example.musicplayer.ui.components.shared

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.musicplayer.ui.theme.Dimensions

@Composable
fun BaseTopBar(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .statusBarsPadding()
            .fillMaxWidth()
            .padding(
                start = Dimensions.paddingMedium,
                end = Dimensions.paddingMedium,
                top = 12.dp,
                bottom = Dimensions.paddingSmall
            ),
        content = content
    )
}