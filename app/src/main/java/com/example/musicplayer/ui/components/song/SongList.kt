package com.example.musicplayer.ui.components.song
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.musicplayer.data.Song
import com.example.musicplayer.ui.theme.Dimensions

@Composable
fun SongList(
    songs: List<Song>,
    onSongClick: (Song) -> Unit,
    onMoreClick: (Song) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(
            top = 16.dp,
            bottom = 70.dp + Dimensions.paddingXLarge
        ),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(songs) { song ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSongClick(song) }
                    .padding(horizontal = Dimensions.paddingMedium)
            ) {
                SongItem(
                    song = song,
                    onMoreClick = { onMoreClick(song) }
                )
            }
        }
    }
}