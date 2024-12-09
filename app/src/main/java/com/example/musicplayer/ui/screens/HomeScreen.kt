package com.example.musicplayer.ui.screens

import android.Manifest
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.musicplayer.ui.components.home.*
import com.example.musicplayer.ui.components.home.TabNav
import com.example.musicplayer.ui.viewmodel.HomeViewModel
import com.example.musicplayer.ui.viewmodel.AudioViewModel
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import com.example.musicplayer.data.AlphabeticDirection
import com.example.musicplayer.data.DurationDirection
import com.example.musicplayer.data.SortOption
import com.example.musicplayer.data.TimeDirection
import com.example.musicplayer.data.sampleSongs
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
    val audioViewModel: AudioViewModel = viewModel()
    val songs by audioViewModel.songs.observeAsState(emptyList())
    val context = LocalContext.current

    // Local state for the displayed (and sorted) songs
    var displayedSongs by remember { mutableStateOf(songs) }

    // Update displayedSongs when the original songs list changes
    LaunchedEffect(songs) {
        displayedSongs = songs
    }

    // Request permissions and fetch songs
    LaunchedEffect(Unit) {
        requestAudioPermission(context) { granted ->
            if (granted) {
                audioViewModel.fetchSongs()
            } else {
                Toast.makeText(context, "Permission denied to access audio files", Toast.LENGTH_SHORT).show()
            }
        }
    }

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
                    // Replace 'sampleSongs' to 'songs' to use music from your device
                    songs = sampleSongs,
                    onSongClick = { song ->
                        miniPlayerViewModel.updateSong(song)
                    },
                    onSortSelected = { option, direction ->
                        // Handle sort on sampleSongs based on option and direction
                        // only sort on sampleSongs, song fetch from local is another case
                        val sortedSongs = when (option) {
                            SortOption.SONG_NAME -> sampleSongs.sortedBy { it.title }
                            SortOption.ARTIST_NAME -> sampleSongs.sortedBy { it.artist }
                            SortOption.TIME_ADDED -> sampleSongs.sortedBy { it.year } // Assuming year is used for time added
                            SortOption.DURATION -> sampleSongs.sortedBy { it.title }
                        }

                        // Adjust sorting based on direction
                        val finalSortedSongs = when (direction) {
                            is AlphabeticDirection.AToZ -> sortedSongs
                            is AlphabeticDirection.ZToA -> sortedSongs.reversed()
                            is TimeDirection.NewToOld -> sortedSongs.reversed()
                            is TimeDirection.OldToNew -> sortedSongs
                            is DurationDirection.ShortToLong -> sortedSongs
                            is DurationDirection.LongToShort -> sortedSongs.reversed()
                        }

                        // Update the UI with the sorted songs
                        displayedSongs = finalSortedSongs
                    }
                )
                1 -> PlaylistScreen(navController = navController)
            }
        }
    }
}



private fun requestAudioPermission(context: Context, onResult: (Boolean) -> Unit) {
    val permission = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_AUDIO
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    Dexter.withContext(context)
        .withPermission(permission)
        .withListener(object : PermissionListener {
            override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                onResult(true) // Permission granted
            }

            override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                onResult(false) // Permission denied
                if (response?.isPermanentlyDenied == true) {
                    Toast.makeText(context, "Permission denied permanently. Enable it in settings.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onPermissionRationaleShouldBeShown(request: PermissionRequest?, token: PermissionToken?) {
                token?.continuePermissionRequest() // Show default rationale behavior
            }
        }).check()
}

