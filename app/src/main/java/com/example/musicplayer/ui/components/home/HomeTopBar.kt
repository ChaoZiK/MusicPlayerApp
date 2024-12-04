package com.example.musicplayer.ui.components.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.musicplayer.ui.components.shared.BaseTopBar
import com.example.musicplayer.ui.components.buttons.MenuButton
import com.example.musicplayer.ui.components.buttons.SearchButton

@Composable
fun HomeTopBar(
    onSearchClick: () -> Unit,
    onMenuClick: () -> Unit
) {
    BaseTopBar {
        MenuButton(
            onClick = onMenuClick,
            modifier = Modifier.align(Alignment.CenterStart)
        )
        SearchButton(
            onClick = onSearchClick,
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}