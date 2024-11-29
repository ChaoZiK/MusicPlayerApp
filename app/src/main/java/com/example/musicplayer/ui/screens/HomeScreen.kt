package com.example.musicplayer.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.musicplayer.data.Song
import com.example.musicplayer.ui.components.home.*
import com.example.musicplayer.ui.components.menu.DrawerContent
import com.example.musicplayer.ui.components.tabs.TabNav
import kotlinx.coroutines.launch

val songs = listOf(
    Song("Song 1", "unknown"),
    Song("Song 2", "unknown"),
    Song("Song 3", "unknown"),
    Song("Song 4", "unknown"),
    Song("Song 5", "unknown"),
    Song("Song 6", "unknown"),
    Song("Song 7", "unknown"),
    Song("Song 8", "unknown"),
    Song("Song 9", "unknown"),
    Song("Song 10", "unknown"),
)

@Composable
fun HomeScreen(
    onSearchClick: () -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedTab by remember { mutableStateOf(0) }

    BackHandler(enabled = drawerState.isOpen) {
        scope.launch { drawerState.close() }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        scrimColor = MaterialTheme.colorScheme.scrim.copy(alpha = 0.5f),
        drawerContent = {
            DrawerContent(
                onItemClick = {
                    scope.launch { drawerState.close() }
                }
            )
        }
    ) {
        Scaffold(
            contentWindowInsets = WindowInsets(0),
            topBar = {
                Column(
                    modifier = Modifier.padding(bottom = 10.dp)
                ) {
                    TopBar(
                        onMenuClick = { scope.launch { drawerState.open() } },
                        onSearchClick = onSearchClick
                    )
                    TabNav(selectedTab = selectedTab, onTabSelected = { selectedTab = it })
                }
            }
        ) { padding ->
            when (selectedTab) {
                0 -> SongsContent(padding = padding, songs = songs)
                1 -> PlaylistContent(padding = padding)
            }
        }
    }
}