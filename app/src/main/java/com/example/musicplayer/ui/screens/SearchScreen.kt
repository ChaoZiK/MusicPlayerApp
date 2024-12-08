package com.example.musicplayer.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.musicplayer.ui.components.search.SearchBar
import com.example.musicplayer.ui.theme.Dimensions
import com.example.musicplayer.ui.viewmodel.SearchViewModel

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    onBackPressed: () -> Unit
) {
    val searchText by viewModel.searchText.collectAsState()

    // Sample list of songs
    val songList = listOf(
        "Like A Rolling Stone",
        "(I Can't Get No) Satisfaction",
        "Imagine",
        "What's Going On",
        "Respect",
        "Good Vibrations",
        "Johnny B. Goode",
        "Hey Jude",
        "Smells Like Teen Spirit",
        "What'd I Say"
    )

    // Filter the song list based on the search text
    val filteredSongs = songList.filter {
        it.contains(searchText, ignoreCase = true)
    }

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

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                horizontal = Dimensions.paddingMedium,
                vertical = Dimensions.paddingSmall
            )
        ) {
            // Search results implementation
            items(filteredSongs) { song ->
                Text(text = song, modifier = Modifier.padding(8.dp))
            }
        }
    }
}