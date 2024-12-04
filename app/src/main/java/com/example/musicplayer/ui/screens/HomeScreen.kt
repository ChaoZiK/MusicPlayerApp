package com.example.musicplayer.ui.screens

import android.Manifest
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.musicplayer.data.Song
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.musicplayer.ui.components.home.*
import com.example.musicplayer.ui.components.menu.DrawerContent
import com.example.musicplayer.ui.components.home.TabNav
import com.example.musicplayer.ui.viewmodel.AudioViewModel
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.coroutines.launch



@Composable
fun HomeScreen(
    onSearchClick: () -> Unit,
    navController: NavController
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedTab by remember { mutableIntStateOf(0) }
    val audioViewModel: AudioViewModel = viewModel()
    val songs by audioViewModel.songs.observeAsState(emptyList())
    val context = LocalContext.current

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

    BackHandler(enabled = drawerState.isOpen) {
        scope.launch { drawerState.close() }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
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
                    TabNav(selectedTab = selectedTab, onTabSelected = { selectedTab = it })
                }
            }
        ) { padding ->
            when (selectedTab) {
                0 -> SongsContent(padding = padding, songs = songs)
                1 -> PlaylistContent(padding = padding, navController = navController)
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

