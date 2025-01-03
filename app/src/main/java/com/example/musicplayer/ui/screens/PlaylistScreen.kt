package com.example.musicplayer.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.musicplayer.data.Playlist
import com.example.musicplayer.data.customPlaylists
import com.example.musicplayer.data.defaultPlaylists
import com.example.musicplayer.data.toSong
import com.example.musicplayer.ui.components.playlist.AddPlaylistDialog
import com.example.musicplayer.ui.components.playlist.PlaylistHeader
import com.example.musicplayer.ui.components.playlist.PlaylistItem
import com.example.musicplayer.ui.components.sheets.CustomPlaylistOptionsSheet
import com.example.musicplayer.ui.components.sheets.DefaultPlaylistOptionsSheet
import com.example.musicplayer.ui.viewmodel.FavoriteListViewModel
import com.example.musicplayer.ui.viewmodel.RecentlyPlayedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistScreen(
    navController: NavController,
    favoriteListViewModel: FavoriteListViewModel = hiltViewModel(),
    recentlyPlayedViewModel: RecentlyPlayedViewModel = hiltViewModel()
) {
    var showAddDialog by remember { mutableStateOf(false) }
    var showOptions by remember { mutableStateOf(false) }
    var selectedPlaylist by remember { mutableStateOf<Playlist?>(null) }
    val favoriteSongs by favoriteListViewModel.favoriteSongs.observeAsState(emptyList())
    val recentlyPlayedSongs by recentlyPlayedViewModel.recentlyPlayedSongs.observeAsState(emptyList())

    val favoriteSongsAsSongs = favoriteSongs.map { it.toSong() }
    val recentlyPlayedSongsAsSongs = recentlyPlayedSongs.map { it.toSong() }

    val favoritePlaylist = Playlist(
        id = "favorite",
        title = "My favorite songs",
        songs = favoriteSongsAsSongs,
        isDefault = true,
        coverImage = null
    )
    val recentlyPlayedPlaylist = Playlist(
        id = "recent",
        title = "Recently played songs",
        songs = recentlyPlayedSongsAsSongs,
        isDefault = true,
        coverImage = null
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            PlaylistHeader(
                defaultPlaylistsCount = defaultPlaylists.size,
                customPlaylistsCount = customPlaylists.size,
                onAddClick = { showAddDialog = true }
            )

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
            ) {
//                // Show Recent playlist
//                items(defaultPlaylists.size) { index ->
//                    val playlist = defaultPlaylists[index]
//                    PlaylistItem(
//                        playlist = playlist,
//                        onPlaylistClick = {
//                            navController.navigate("playlist/${playlist.id}")
//                        },
//                        onMoreClick = {
//                            selectedPlaylist = playlist
//                            showOptions = true
//                        }
//                    )
//                }

                // Add Recently Played item
                item {
                    PlaylistItem(
                        playlist = recentlyPlayedPlaylist,
                        modifier = Modifier.padding(bottom = 8.dp),
                        onPlaylistClick = {
                            navController.navigate("recently_played")
                        },
                        onMoreClick = {
                            selectedPlaylist = recentlyPlayedPlaylist
                            showOptions = true
                        }
                    )
                }

                item {
                    PlaylistItem(
                        playlist = favoritePlaylist,
                        modifier = Modifier.padding(bottom = 8.dp),
                        onPlaylistClick = {
                            navController.navigate("favorites")
                        },
                        onMoreClick = {
                            selectedPlaylist = favoritePlaylist
                            showOptions = true
                        }
                    )
                }

                item {
                    Text(
                        text = "My playlists (${customPlaylists.size})",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(
                            start = 16.dp,
                            top = 24.dp,
                            bottom = 16.dp
                        ),
                    )
                }

                items(customPlaylists.size) { index ->
                    val playlist = customPlaylists[index]
                    PlaylistItem(
                        playlist = playlist,
                        onPlaylistClick = {
                            navController.navigate("playlist/${playlist.id}")
                        },
                        onMoreClick = {
                            selectedPlaylist = playlist
                            showOptions = true
                        }
                    )
                }
            }
        }
    }

    if (showAddDialog) {
        AddPlaylistDialog(
            onDismiss = { showAddDialog = false },
            onCreatePlaylist = {
                // Handle playlist creation
                showAddDialog = false
            }
        )
    }

    if (showOptions && selectedPlaylist != null) {
        ModalBottomSheet(
            onDismissRequest = {
                showOptions = false
                selectedPlaylist = null
            },
            containerColor = MaterialTheme.colorScheme.surface,
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            dragHandle = {
                Box(
                    Modifier
                        .padding(top = 15.dp, bottom = 5.dp)
                        .width(44.dp)
                        .height(4.dp)
                        .background(
                            MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                            MaterialTheme.shapes.small
                        )
                )
            }
        ) {
            if (selectedPlaylist!!.isDefault) {
                DefaultPlaylistOptionsSheet(
                    playlist = selectedPlaylist!!,
                    onDismiss = {
                        showOptions = false
                        selectedPlaylist = null
                    }
                )
            } else {
                CustomPlaylistOptionsSheet(
                    playlist = selectedPlaylist!!,
                    onDismiss = {
                        showOptions = false
                        selectedPlaylist = null
                    }
                )
            }
        }
    }
}