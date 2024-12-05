package com.example.musicplayer.ui.components.playlist

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.musicplayer.ui.components.buttons.BackButton
import com.example.musicplayer.ui.components.buttons.SearchButton
import com.example.musicplayer.ui.components.shared.BaseTopBar

@Composable
fun PlaylistDetailTopBar(
    onBackClick: () -> Unit,
    playlistTitle: String,
    onSearchClick: () -> Unit
) {
    BaseTopBar {
        BackButton(
            onBackClick = onBackClick,
            modifier = Modifier.align(Alignment.CenterStart)
        )

        Text(
            text = playlistTitle,
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            ),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.align(Alignment.Center)
        )

        SearchButton(
            onClick = onSearchClick,
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}