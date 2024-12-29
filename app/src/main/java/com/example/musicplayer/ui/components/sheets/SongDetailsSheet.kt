package com.example.musicplayer.ui.components.sheets

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.musicplayer.data.Song
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.unit.dp
import com.example.musicplayer.ui.components.buttons.ActionButton
import com.example.musicplayer.ui.components.buttons.CancelButton

@Composable
fun SongDetailsSheet(
    song: Song,
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Text(
            "Details",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        DetailItem("Title", song.title)
        DetailItem("Album", song.album)
        DetailItem("Artist", song.artist)
        DetailItem("Genre", song.genre ?: "<unknown>")
        DetailItem("Duration", song.duration)
        DetailItem("Size", song.size)
        DetailItem("Location", song.path)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CancelButton(
                onClick = onDismiss,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun DetailItem(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(label, style = MaterialTheme.typography.bodyMedium)
        Text(
            value,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}