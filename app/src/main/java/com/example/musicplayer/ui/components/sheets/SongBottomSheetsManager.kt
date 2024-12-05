package com.example.musicplayer.ui.components.sheets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.musicplayer.data.Song

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun SongBottomSheetsManager(
    selectedSong: Song?,
    showOptions: Boolean,
    showDetails: Boolean,
    onOptionsDismiss: () -> Unit,
    onDetailsClick: () -> Unit,
    onDetailsDismiss: () -> Unit
) {
    val dragHandle: @Composable () -> Unit = {
        Box(
            Modifier
                .padding(top = 15.dp, bottom = 5.dp)
                .width(44.dp)
                .height(4.dp)
                .background(
                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                    MaterialTheme.shapes.small
                )
        )
    }

    if (showOptions && selectedSong != null) {
        ModalBottomSheet(
            onDismissRequest = onOptionsDismiss,
            containerColor = MaterialTheme.colorScheme.surface,
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            dragHandle = dragHandle
        ) {
            SongOptionsSheet(
                song = selectedSong,
                onDismiss = onOptionsDismiss,
                onInfoClick = onDetailsClick
            )
        }
    }

    if (showDetails && selectedSong != null) {
        ModalBottomSheet(
            onDismissRequest = onDetailsDismiss,
            containerColor = MaterialTheme.colorScheme.surface,
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            dragHandle = dragHandle
        ) {
            SongDetailsSheet(
                song = selectedSong,
                onDismiss = onDetailsDismiss
            )
        }
    }
}