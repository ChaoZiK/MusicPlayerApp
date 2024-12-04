package com.example.musicplayer.ui.components.playlist

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musicplayer.ui.components.buttons.ActionButton
import com.example.musicplayer.ui.components.buttons.CancelButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPlaylistDialog(
    onDismiss: () -> Unit,
    onCreatePlaylist: (String) -> Unit
) {
    var playlistName by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surface,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
        ) {
            Text(
                text = "Create new playlist",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            BasicTextField(
                value = playlistName,
                onValueChange = {
                    if (it.length <= 40) {
                        playlistName = it
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .height(48.dp)
                    .animateContentSize(),
                singleLine = true,
                cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 16.sp,
                ),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrectEnabled = false,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                decorationBox = { innerTextField ->
                    PlaylistNameFieldDecoration(
                        playlistName = playlistName,
                        characterCount = "${playlistName.length}/40",
                        innerTextField = innerTextField
                    )
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CancelButton(
                    onClick = onDismiss,
                    modifier = Modifier.weight(1f)
                )

                ActionButton(
                    text = "Create",
                    onClick = {
                        if (playlistName.isNotBlank()) {
                            onCreatePlaylist(playlistName)
                            onDismiss()
                        }
                    },
                    enabled = playlistName.isNotBlank(),
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

