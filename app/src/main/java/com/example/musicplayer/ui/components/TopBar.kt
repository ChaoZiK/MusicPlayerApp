package com.example.musicplayer.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.musicplayer.ui.theme.Dimensions
import com.example.musicplayer.ui.theme.AppIcons
import com.example.musicplayer.ui.theme.PrimaryColor

@Composable
fun TopBar() {
    Box(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxWidth()
            .padding(
                horizontal = Dimensions.paddingMedium,
                vertical = Dimensions.paddingSmall
            )
    ) {
        IconButton(
            onClick = { },
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                painter = painterResource(AppIcons.menuBurger),
                contentDescription = "Menu",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(Dimensions.iconSizeMedium)
            )
        }

        IconButton(
            onClick = { },
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Icon(
                painter = painterResource(AppIcons.search),
                contentDescription = "Search",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(Dimensions.iconSizeLarge)
            )
        }
    }
}
