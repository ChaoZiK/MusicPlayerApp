package com.example.musicplayer.ui.components.home

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.musicplayer.ui.theme.AppIcons
import com.example.musicplayer.ui.theme.Dimensions

@Composable
fun TopBar(
    onSearchClick: () -> Unit,
    onMenuClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxWidth()
            .padding(
                start = Dimensions.paddingMedium,
                end = Dimensions.paddingMedium,
                top = 12.dp,
                bottom = Dimensions.paddingSmall
            )    ) {
        IconButton(
            onClick = onMenuClick,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(40.dp)
        ) {
            Icon(
                painter = painterResource(AppIcons.menuBurger),
                contentDescription = "Menu",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(24.dp)
            )
        }

        IconButton(
            onClick = onSearchClick,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(40.dp)
                .animateContentSize()
        ) {
            Icon(
                painter = painterResource(AppIcons.search),
                contentDescription = "Search",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}