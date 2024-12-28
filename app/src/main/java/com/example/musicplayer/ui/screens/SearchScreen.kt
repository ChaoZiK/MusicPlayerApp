package com.example.musicplayer.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.musicplayer.data.Song
import com.example.musicplayer.ui.components.search.SearchBar
import com.example.musicplayer.ui.components.song.SongList
import com.example.musicplayer.ui.theme.Dimensions
import com.example.musicplayer.ui.viewmodel.SearchViewModel

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    onBackPressed: () -> Unit,
    onSongClick: (Song) -> Unit
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
            SongList(
                songs = searchResults,
                onSongClick = onSongClick,
                onMoreClick = {}
            )
        }
    }
}
