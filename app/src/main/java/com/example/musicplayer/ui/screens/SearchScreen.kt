package com.example.musicplayer.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.musicplayer.data.Song
import com.example.musicplayer.data.toSong
import com.example.musicplayer.ui.components.search.SearchBar
import com.example.musicplayer.ui.components.shared.SongsContentLayout
import com.example.musicplayer.ui.components.song.SongList
import com.example.musicplayer.ui.theme.Dimensions
import com.example.musicplayer.ui.viewmodel.FullPlayerViewModel
import com.example.musicplayer.ui.viewmodel.MiniPlayerViewModel
import com.example.musicplayer.ui.viewmodel.SearchViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    onBackPressed: () -> Unit,
    onSongClick: (Song) -> Unit,
    miniPlayerViewModel: MiniPlayerViewModel,
    fullPlayerViewModel: FullPlayerViewModel,
    coroutineScope: CoroutineScope
) {
    val searchText by viewModel.searchText.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SearchBar(
            onBackClick = {
                viewModel.deactivateSearch()
                onBackPressed()
            },
            searchText = searchText,
            onSearchTextChanged = { viewModel.onSearchTextChange(it) }
        )

        if (searchResults.isEmpty() && searchText.isNotEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No results found",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            SongsContentLayout(
                songs = searchResults,
                showTopBar = false, // Disable top bar in the layout
                isSearchScreen = true,
                onBackPressed = null,
                onSearchClick = null,
                onShuffleClick = {
                    // Shuffle and play songs from search results
                    fullPlayerViewModel.updatePlaylist(searchResults)
                    fullPlayerViewModel.shuffleAndPlay()
                },
                onPlayClick = {
                    // Play the first song from search results
                    fullPlayerViewModel.updatePlaylist(searchResults)
                    fullPlayerViewModel.playFirstSong()
                },
                onSongClick = { song ->
                    coroutineScope.launch {
                        // Update the playlist in PlayerRepository
                        fullPlayerViewModel.updatePlaylist(searchResults)

                        // Play the selected song
                        val index = searchResults.indexOf(song)
                        fullPlayerViewModel.playSongByIndex(index)
                    }
                    onSongClick(song)
                },
                miniPlayerViewModel = miniPlayerViewModel
            )
        }
    }
}
