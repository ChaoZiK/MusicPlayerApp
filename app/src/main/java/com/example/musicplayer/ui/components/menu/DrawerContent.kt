package com.example.musicplayer.ui.components.menu


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musicplayer.data.Playlist
import com.example.musicplayer.ui.theme.AppIcons

sealed class DrawerIcon {
    data class PainterIcon(val painter: Painter) : DrawerIcon()
    data class VectorIcon(val imageVector: ImageVector) : DrawerIcon()
}

data class DrawerItemData(
    val icon: DrawerIcon,
    val label: String,
    val onClick: () -> Unit
)

val playlists = listOf(
    Playlist("1", "My Rock Playlist"),
    Playlist("2", "Chill Music"),
    Playlist("3", "Workout Mix"),
    Playlist("4", "Road Trip Songs"),
    Playlist("5", "90s Hits"),
    Playlist("6", "Study Music"),
    Playlist("7", "Party Mix"),
    Playlist("8", "Acoustic Covers"),
    Playlist("9", "90s Hits"),
    Playlist("10", "Study Music"),
    Playlist("11", "Party Mix"),
    Playlist("12", "Acoustic Covers")
)

@Composable
fun DrawerContent(
    onItemClick: (String) -> Unit
) {
    var selectedItem by rememberSaveable { mutableStateOf("") }

    ModalDrawerSheet(
        modifier = Modifier.fillMaxWidth(0.75f),
        drawerContainerColor = MaterialTheme.colorScheme.surface,
        drawerContentColor = MaterialTheme.colorScheme.onSurface
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp),
        ) {
            Text(
                "Music Player",
                modifier = Modifier.padding(
                    start = 25.dp,
                    end = 25.dp,
                    bottom = 25.dp,
                    top = 40.dp
                ),
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 32.sp),
            )

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 25.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onSurface
            )

            DrawerItem(
                item = DrawerItemData(
                    icon = DrawerIcon.PainterIcon(painterResource(AppIcons.favourite)),
                    label = "Favorites"
                ) {
                    selectedItem = "favorite"
                    onItemClick("favorite")
                },
                selectedItem = selectedItem
            )

            DrawerItem(
                item = DrawerItemData(
                    icon = DrawerIcon.PainterIcon(painterResource(AppIcons.history)),
                    label = "Recently Played"
                ) {
                    selectedItem = "recent"
                    onItemClick("recent")
                },
                selectedItem = selectedItem,
            )

            DrawerItem(
                item = DrawerItemData(
                    icon = DrawerIcon.PainterIcon(painterResource(AppIcons.playlists)),
                    label = "Playlists"
                ) {
                    selectedItem = "playlists"
                    onItemClick("playlists")
                },
                selectedItem = selectedItem
            )

            Spacer(modifier = Modifier.weight(1f))

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .align(Alignment.CenterHorizontally),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onSurface
            )

            DrawerItem(
                item = DrawerItemData(
                    icon = DrawerIcon.PainterIcon(painterResource(AppIcons.settings)),
                    label = "Settings"
                ) {
                    selectedItem = "settings"
                    onItemClick("settings")
                },
                selectedItem = selectedItem
            )
        }
    }
}


