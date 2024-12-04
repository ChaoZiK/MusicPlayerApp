package com.example.musicplayer

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.musicplayer.data.customPlaylists
import com.example.musicplayer.data.defaultPlaylists
import com.example.musicplayer.ui.screens.HomeScreen
import com.example.musicplayer.ui.screens.PlaylistDetailScreen
import com.example.musicplayer.ui.screens.SearchScreen
import com.example.musicplayer.ui.theme.AppIcons.playlist
import com.example.musicplayer.ui.viewmodel.SearchViewModel

@Composable
fun MusicPlayerNavigation() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val viewModel: SearchViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            AnimatedScreen(visible = currentBackStackEntry?.destination?.route == "home") {
                HomeScreen(
                    onSearchClick = {
                        viewModel.activateSearch()
                        navController.navigate("search")
                    }, navController = navController
                )
            }
        }
        composable("search") {
            AnimatedScreen(visible = currentBackStackEntry?.destination?.route == "search") {
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
                AnimatedScreen(visible = currentBackStackEntry?.destination?.route?.startsWith("playlist") == true) {
                    PlaylistDetailScreen(
                        playlist = it,
                        onBackPressed = { navController.navigateUp() },
                        onSearchClick = {
                            viewModel.activateSearch()
                            navController.navigate("search")
                        }
                    )
                }
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