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
                color = MaterialTheme.colorScheme.onSurface.copy(0.5f)
            )

            Spacer(modifier = Modifier.weight(0.5f))


            DrawerItem(
                item = DrawerItemData(
                    icon = DrawerIcon.PainterIcon(painterResource(AppIcons.info)),
                    label = "Information"
                ) {
                    selectedItem = "information"
                    onItemClick("information")
                }
            )

            DrawerItem(
                item = DrawerItemData(
                    icon = DrawerIcon.PainterIcon(painterResource(AppIcons.feedback)),
                    label = "Feedback"
                ) {
                    selectedItem = "feedback"
                    onItemClick("feedback")
                },
            )

            DrawerItem(
                item = DrawerItemData(
                    icon = DrawerIcon.PainterIcon(painterResource(AppIcons.exit)),
                    label = "Exit"
                ) {
                    selectedItem = "exit"
                    onItemClick("exit")
                }
            )

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}


