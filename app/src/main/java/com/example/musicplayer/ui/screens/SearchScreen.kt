package com.example.musicplayer.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.musicplayer.ui.components.search.SearchBar
import com.example.musicplayer.ui.theme.Dimensions
import com.example.musicplayer.ui.viewmodel.SearchViewModel

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    onBackPressed: () -> Unit
) {
    val searchText by viewModel.searchText.collectAsState()

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
        }
    }
}
