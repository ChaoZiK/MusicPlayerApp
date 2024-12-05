package com.example.musicplayer

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.musicplayer.data.customPlaylists
import com.example.musicplayer.data.defaultPlaylists
import com.example.musicplayer.ui.components.menu.DrawerContent
import com.example.musicplayer.ui.components.shared.MiniPlayer
import com.example.musicplayer.ui.screens.HomeScreen
import com.example.musicplayer.ui.screens.PlaylistDetailScreen
import com.example.musicplayer.ui.screens.SearchScreen
import com.example.musicplayer.ui.theme.Dimensions
import com.example.musicplayer.ui.viewmodel.MiniPlayerViewModel
import com.example.musicplayer.ui.viewmodel.SearchViewModel
import kotlinx.coroutines.launch

@Composable
fun MusicPlayerApp() {
    val navController = rememberNavController()
    val currentEntry by navController.currentBackStackEntryAsState()

    val showMiniPlayer = currentEntry?.destination?.route?.let { route ->
        route == "home" || route.startsWith("playlist/")
    } ?: false

    val viewModel: SearchViewModel = hiltViewModel()
    val miniPlayerViewModel: MiniPlayerViewModel = hiltViewModel()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        scrimColor = MaterialTheme.colorScheme.scrim.copy(alpha = 0.5f),
        drawerContent = {
            DrawerContent(
                onItemClick = {
                    scope.launch { drawerState.close() }
                }
            )
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier.fillMaxSize()
            ) {
                composable("home") {
                    AnimatedScreen(visible = currentEntry?.destination?.route == "home") {
                        HomeScreen(
                            onSearchClick = {
                                viewModel.activateSearch()
                                navController.navigate("search")
                            },
                            navController = navController,
                            miniPlayerViewModel = miniPlayerViewModel,
                            onMenuClick = { scope.launch { drawerState.open() } }
                        )
                    }
                }
                composable("search") {
                    AnimatedScreen(visible = currentEntry?.destination?.route == "search") {
                        SearchScreen(
                            viewModel = viewModel,
                            onBackPressed = { navController.navigateUp() }
                        )
                    }
                }
                composable(
                    route = "playlist/{playlistId}",
                    arguments = listOf(navArgument("playlistId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val playlistId = backStackEntry.arguments?.getString("playlistId")
                    val playlist = (defaultPlaylists + customPlaylists).find { it.id == playlistId }

                    playlist?.let {
                        AnimatedScreen(visible = currentEntry?.destination?.route?.startsWith("playlist") == true) {
                            PlaylistDetailScreen(
                                playlist = it,
                                onBackPressed = { navController.navigateUp() },
                                onSearchClick = {
                                    viewModel.activateSearch()
                                    navController.navigate("search")
                                },
                                onSongClick = { song ->
                                    miniPlayerViewModel.updateSong(song)
                                },
                                onSortSelected = { option, direction ->
                                    // Handle sort
                                }
                            )
                        }
                    }
                }
            }

            if (showMiniPlayer) {
                MiniPlayer(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = Dimensions.paddingXLarge),
                    viewModel = miniPlayerViewModel
                )
            }
        }
    }
}

@Composable
fun AnimatedScreen(
    visible: Boolean,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        content()
    }
}