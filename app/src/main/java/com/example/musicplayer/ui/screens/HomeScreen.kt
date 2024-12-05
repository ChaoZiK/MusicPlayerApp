package com.example.musicplayer.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.musicplayer.data.sampleSongs
import com.example.musicplayer.ui.components.home.*
import com.example.musicplayer.ui.components.menu.DrawerContent
import com.example.musicplayer.ui.components.home.TabNav
import com.example.musicplayer.ui.viewmodel.HomeViewModel
import kotlinx.coroutines.launch
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import com.example.musicplayer.ui.viewmodel.MiniPlayerViewModel

@Composable
fun HomeScreen(
    onSearchClick: () -> Unit,
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
    miniPlayerViewModel: MiniPlayerViewModel,
    onMenuClick: () -> Unit
) {
    val selectedTab by viewModel.selectedTab.collectAsState()

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { 2 }
    )

    LaunchedEffect(selectedTab) {
        pagerState.animateScrollToPage(selectedTab)
    }

    LaunchedEffect(pagerState.currentPage) {
        viewModel.setSelectedTab(pagerState.currentPage)
    }

    Scaffold(
        contentWindowInsets = WindowInsets(0),
        topBar = {
            Column(
                modifier = Modifier.padding(bottom = 10.dp)
            ) {
                HomeTopBar(
                    onMenuClick = onMenuClick,
                    onSearchClick = onSearchClick
                )
                TabNav(
                    selectedTab = selectedTab,
                    onTabSelected = {
                        viewModel.setSelectedTab(it)
                    }
                )
            }
        }
    ) { innerPadding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) { page ->
            when (page) {
                0 -> SongsScreen(
                    songs = sampleSongs,
                    onSongClick = { song ->
                        miniPlayerViewModel.updateSong(song)
                    },
                    onSortSelected = { option, direction ->
                        // Handle sort
                    }
                )
                1 -> PlaylistScreen(navController = navController)
            }
        }
    }
}

