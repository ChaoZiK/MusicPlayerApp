package com.example.musicplayer

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.musicplayer.ui.screens.HomeScreen
import com.example.musicplayer.ui.screens.SearchScreen
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
                HomeScreen(onSearchClick = {
                    viewModel.activateSearch()
                    navController.navigate("search")
                })
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