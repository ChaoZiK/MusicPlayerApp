package com.example.musicplayer


import android.os.Process
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.musicplayer.data.defaultPlaylists
import com.example.musicplayer.data.toSong
import com.example.musicplayer.ui.animations.Transitions
import com.example.musicplayer.ui.components.dialogs.ExitDialog
import com.example.musicplayer.ui.components.menu.DrawerContent
import com.example.musicplayer.ui.components.player.MiniPlayer
import com.example.musicplayer.ui.screens.FavoriteListScreen
import com.example.musicplayer.ui.screens.FeedbackScreen
import com.example.musicplayer.ui.screens.FullPlayerScreen
import com.example.musicplayer.ui.screens.HomeScreen
import com.example.musicplayer.ui.screens.InformationScreen
import com.example.musicplayer.ui.screens.PlaylistDetailScreen
import com.example.musicplayer.ui.screens.RecentlyPlayedListScreen
import com.example.musicplayer.ui.screens.SearchScreen
import com.example.musicplayer.ui.theme.Dimensions
import com.example.musicplayer.ui.viewmodel.AudioViewModel
import com.example.musicplayer.ui.viewmodel.FavoriteListViewModel
import com.example.musicplayer.ui.viewmodel.FullPlayerViewModel
import com.example.musicplayer.ui.viewmodel.MiniPlayerViewModel
import com.example.musicplayer.ui.viewmodel.RecentlyPlayedViewModel
import com.example.musicplayer.ui.viewmodel.SearchViewModel
import kotlinx.coroutines.launch

private object Destinations {
    const val HOME = "home"
    const val SEARCH = "search"
    const val PLAYLIST = "playlist/{playlistId}"
    const val FULL_PLAYER = "full_player"
    const val INFORMATION = "information"
    const val FEEDBACK = "feedback"
    const val FAVORITES = "favorites"
    const val RECENTLY_PLAYED = "recently_played"
}

@Composable
private fun MusicNavGraph(
    navController: NavHostController,
    viewModel: SearchViewModel,
    miniPlayerViewModel: MiniPlayerViewModel,
    fullPlayerViewModel: FullPlayerViewModel,
    onOpenDrawer: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.HOME,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(
            route = Destinations.HOME,
            enterTransition = Transitions.Navigation.defaultEnter,
            exitTransition = Transitions.Navigation.defaultExit
        ) {
            val audioViewModel: AudioViewModel = hiltViewModel()
            val songs by audioViewModel.songs.observeAsState(emptyList())
            HomeScreen(
                onSearchClick = {
                    viewModel.activateSearch(songs)
                    navController.navigate(Destinations.SEARCH)
                },
                navController = navController,
                miniPlayerViewModel = miniPlayerViewModel,
                onMenuClick = onOpenDrawer
            )
        }

        composable(
            route = Destinations.SEARCH,
            enterTransition = Transitions.Navigation.defaultEnter,
            exitTransition = Transitions.Navigation.defaultExit
        ) {
            SearchScreen(
                viewModel = viewModel,
                onBackPressed = { navController.navigateUp() },
                onSongClick = { song ->
                   fullPlayerViewModel.updatePlaylist(listOf(song))
                    fullPlayerViewModel.playSongByIndex(0) // Play the selected song
                },
                miniPlayerViewModel = miniPlayerViewModel,
                fullPlayerViewModel = fullPlayerViewModel,
                coroutineScope = rememberCoroutineScope()
            )
        }

//        composable(
//            route = Destinations.PLAYLIST,
//            arguments = listOf(navArgument("playlistId") { type = NavType.StringType }),
//            enterTransition = Transitions.Navigation.defaultEnter,
//            exitTransition = Transitions.Navigation.defaultExit
//        ) { backStackEntry ->
//            val playlistId = backStackEntry.arguments?.getString("playlistId")
//            val playlist = (defaultPlaylists + customPlaylists).find { it.id == playlistId }
//
//            playlist?.let {
//                PlaylistDetailScreen(
//                    playlist = it,
//                    onBackPressed = { navController.navigateUp() },
//                    onSearchClick = {
//                        viewModel.activateSearch()
//                        navController.navigate(Destinations.SEARCH)
//                    },
//                    onSongClick = { song -> miniPlayerViewModel.updateSong(song) },
//                    onSortSelected = { option, direction -> /* Handle sort */ },
//                    miniPlayerViewModel = miniPlayerViewModel
//                )
//            }
//        }

        composable(
            route = Destinations.FULL_PLAYER,
            enterTransition = Transitions.Navigation.fullPlayerEnter,
            exitTransition = Transitions.Navigation.fullPlayerExit
        ) {
            FullPlayerScreen(onBackClick = { navController.navigateUp() })
        }

        composable(
            route = Destinations.INFORMATION,
            enterTransition = Transitions.Navigation.defaultEnter,
            exitTransition = Transitions.Navigation.defaultExit
        ) {
            InformationScreen(onBackClick = { navController.navigateUp() })
        }

        composable(
            route = Destinations.FEEDBACK,
            enterTransition = Transitions.Navigation.defaultEnter,
            exitTransition = Transitions.Navigation.defaultExit
        ) {
            FeedbackScreen(onBackClick = { navController.navigateUp() })
        }


        composable(
            route = Destinations.FAVORITES,
            enterTransition = Transitions.Navigation.defaultEnter,
            exitTransition = Transitions.Navigation.defaultExit
        ) {
            val favoriteViewModel: FavoriteListViewModel = hiltViewModel()
            val favoriteSongs by favoriteViewModel.favoriteSongs.observeAsState(emptyList())

            FavoriteListScreen(
                onSongClick = { song ->
                    navController.navigate("${Destinations.FULL_PLAYER}/${song.songId}")
                },
                onBackClick = { navController.navigateUp() },
                onSearchClick = {
                    viewModel.activateSearch(favoriteSongs.map { it.toSong() })
                    navController.navigate(Destinations.SEARCH)
                },
                miniPlayerViewModel = miniPlayerViewModel,
                fullPlayerViewModel = fullPlayerViewModel,
                coroutineScope = rememberCoroutineScope()
            )
        }

        composable(
            route = Destinations.RECENTLY_PLAYED,
            enterTransition = Transitions.Navigation.defaultEnter,
            exitTransition = Transitions.Navigation.defaultExit
        ) {
            val recentlyPlayedViewModel: RecentlyPlayedViewModel = hiltViewModel()
            val recentlyPlayedSongs by recentlyPlayedViewModel.recentlyPlayedSongs.observeAsState(emptyList())

            RecentlyPlayedListScreen(
                onBackClick = { navController.navigateUp() },
                onSearchClick = {
                    viewModel.activateSearch(recentlyPlayedSongs.map { it.toSong() })
                    navController.navigate(Destinations.SEARCH)
                },
                onSongClick = { song ->
                    navController.navigate("${Destinations.FULL_PLAYER}/${song.songId}")
                },
                miniPlayerViewModel = miniPlayerViewModel,
                fullPlayerViewModel = fullPlayerViewModel,
                coroutineScope = rememberCoroutineScope()
            )
        }

    }
}

@Composable
fun MusicPlayerApp() {
    val navController = rememberNavController()
    val currentEntry by navController.currentBackStackEntryAsState()
    val viewModel: SearchViewModel = hiltViewModel()
    val miniPlayerViewModel: MiniPlayerViewModel = hiltViewModel()
    val fullPlayerViewModel: FullPlayerViewModel = hiltViewModel()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var showExitDialog by remember { mutableStateOf(false) }

    val showMiniPlayer = currentEntry?.destination?.route?.let { route ->
        (route == Destinations.HOME || route == Destinations.RECENTLY_PLAYED || route == Destinations.FAVORITES || route == Destinations.SEARCH ||route.startsWith("playlist/")) &&
                miniPlayerViewModel.currentSong.collectAsState().value != null
    } ?: false


    ModalNavigationDrawer(
        drawerState = drawerState,
        scrimColor = MaterialTheme.colorScheme.scrim.copy(alpha = 0.5f),
        drawerContent = {
            DrawerContent(
                onItemClick = { route ->
                    scope.launch {
                        drawerState.close()
                        when (route) {
                            "information" -> navController.navigate(Destinations.INFORMATION)
                            "feedback" -> navController.navigate(Destinations.FEEDBACK)
                            "exit" -> showExitDialog = true
                        }
                    }
                }
            )
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            MusicNavGraph(
                navController = navController,
                viewModel = viewModel,
                miniPlayerViewModel = miniPlayerViewModel,
                fullPlayerViewModel = fullPlayerViewModel,
                onOpenDrawer = { scope.launch { drawerState.open() } }
            )

            AnimatedVisibility(
                visible = showMiniPlayer,
                enter = Transitions.fadeIn,
                exit = Transitions.fadeOut,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = Dimensions.paddingXLarge)
            ) {
                MiniPlayer(
                    viewModel = miniPlayerViewModel,
                    onExpandClick = { navController.navigate(Destinations.FULL_PLAYER) }
                )
            }

            if (showExitDialog) {
                ExitDialog(
                    onDismiss = { showExitDialog = false },
                    onConfirm = {
                        Process.killProcess(Process.myPid())
                    }
                )
            }
        }
    }
}
