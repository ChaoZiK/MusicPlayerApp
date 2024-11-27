package com.example.musicplayer.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.musicplayer.ui.viewmodel.SearchViewModel

@Composable
fun MainScreen() {
    val viewModel: SearchViewModel = hiltViewModel()
    val isSearchActive by viewModel.isSearchActive.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(
            visible = !isSearchActive,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            HomeScreen(
                onSearchClick = { viewModel.activateSearch() }
            )
        }

        AnimatedVisibility(
            visible = isSearchActive,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            SearchScreen(viewModel = viewModel)
        }
    }
}