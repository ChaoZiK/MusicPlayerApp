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

@Composable
fun HomeScreen(
    onSearchClick: () -> Unit,
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val selectedTab by viewModel.selectedTab.collectAsState()

    // Thêm pagerState
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { 2 }
    )

    // Sync giữa pager và tab
    LaunchedEffect(selectedTab) {
        pagerState.animateScrollToPage(selectedTab)
    }

    LaunchedEffect(pagerState.currentPage) {
        viewModel.setSelectedTab(pagerState.currentPage)
    }

    BackHandler(enabled = drawerState.isOpen) {
        scope.launch { drawerState.close() }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        scrimColor = MaterialTheme.colorScheme.scrim.copy(alpha = 0.5f),
        drawerContent = {
            DrawerContent(
                onItemClick = {
                    scope.launch { drawerState.close() }
                }
            )
        }
    ) {
        Scaffold(
            contentWindowInsets = WindowInsets(0),
            topBar = {
                Column(
                    modifier = Modifier.padding(bottom = 10.dp)
                ) {
                    HomeTopBar(
                        onMenuClick = { scope.launch { drawerState.open() } },
                        onSearchClick = onSearchClick
                    )
                    TabNav(
                        selectedTab = selectedTab,
                        onTabSelected = {
                            scope.launch {
                                pagerState.animateScrollToPage(it)
                            }
                        }
                    )
                }
            }
        ) { padding ->
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                when (page) {
                    0 -> SongsContent(padding = padding, songs = sampleSongs)
                    1 -> PlaylistContent(padding = padding, navController = navController)
                }
            }
        }
    }
}