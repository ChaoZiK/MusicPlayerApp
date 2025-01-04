package com.example.musicplayer.ui.components.sheets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.example.musicplayer.data.AlphabeticDirection
import com.example.musicplayer.data.DurationDirection
import com.example.musicplayer.data.SortDirection
import com.example.musicplayer.data.SortOption
import com.example.musicplayer.ui.components.buttons.ActionButton
import com.example.musicplayer.ui.components.buttons.CancelButton

@Composable
fun SortSheet(
    onDismiss: () -> Unit,
    onSortOptionSelected: (SortOption, SortDirection) -> Unit
) {
    var selectedOption by remember { mutableStateOf(SortOption.SONG_NAME) }
    var selectedDirection by remember { mutableStateOf<SortDirection>(AlphabeticDirection.AToZ) }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Sort by",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
        )

        ListItem(
            modifier = Modifier
                .clickable {
                    selectedOption = SortOption.SONG_NAME
                    selectedDirection = AlphabeticDirection.AToZ
                }
                .height(48.dp),
            headlineContent = {
                Box(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Text(
                        "Song name",
                        color = if (selectedOption == SortOption.SONG_NAME)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.onSurface
                    )
                }
            },
            trailingContent = {
                Box(modifier = Modifier.padding(end = 20.dp)) {
                    RadioButton(
                        selected = selectedOption == SortOption.SONG_NAME,
                        onClick = {
                            selectedOption = SortOption.SONG_NAME
                            selectedDirection = AlphabeticDirection.AToZ
                        }
                    )
                }
            }
        )

        ListItem(
            modifier = Modifier
                .clickable {
                    selectedOption = SortOption.ARTIST_NAME
                    selectedDirection = AlphabeticDirection.AToZ
                }
                .height(48.dp),
            headlineContent = {
                Box(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Text(
                        "Artist name",
                        color = if (selectedOption == SortOption.ARTIST_NAME)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.onSurface
                    )
                }
            },
            trailingContent = {
                Box(modifier = Modifier.padding(end = 20.dp)) {
                    RadioButton(
                        selected = selectedOption == SortOption.ARTIST_NAME,
                        onClick = {
                            selectedOption = SortOption.ARTIST_NAME
                            selectedDirection = AlphabeticDirection.AToZ
                        }
                    )
                }
            }
        )

        ListItem(
            modifier = Modifier
                .clickable {
                    selectedOption = SortOption.DURATION
                    selectedDirection = DurationDirection.ShortToLong
                }
                .height(48.dp),
            headlineContent = {
                Box(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Text(
                        "Duration",
                        color = if (selectedOption == SortOption.DURATION)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.onSurface
                    )
                }
            },
            trailingContent = {
                Box(modifier = Modifier.padding(end = 20.dp)) {
                    RadioButton(
                        selected = selectedOption == SortOption.DURATION,
                        onClick = {
                            selectedOption = SortOption.DURATION
                            selectedDirection = DurationDirection.ShortToLong
                        }
                    )
                }
            }
        )

        Box(modifier = Modifier.padding(horizontal = 20.dp)) {
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        }

        when (selectedOption) {
            SortOption.SONG_NAME, SortOption.ARTIST_NAME -> {
                DirectionSelector(
                    firstOption = AlphabeticDirection.AToZ,
                    secondOption = AlphabeticDirection.ZToA,
                    selectedDirection = selectedDirection
                ) { selectedDirection = it }
            }

            SortOption.DURATION -> {
                DirectionSelector(
                    firstOption = DurationDirection.ShortToLong,
                    secondOption = DurationDirection.LongToShort,
                    selectedDirection = selectedDirection
                ) { selectedDirection = it }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CancelButton(
                onClick = onDismiss,
                modifier = Modifier.weight(1f)
            )

            ActionButton(
                text = "OK",
                onClick = {
                    onSortOptionSelected(selectedOption, selectedDirection)
                    onDismiss()
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun DirectionSelector(
    firstOption: SortDirection,
    secondOption: SortDirection,
    selectedDirection: SortDirection,
    onDirectionSelected: (SortDirection) -> Unit
) {
    Column {
        ListItem(
            modifier = Modifier
                .clickable { onDirectionSelected(firstOption) }
                .height(48.dp),
            headlineContent = {
                Box(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Text(
                        firstOption.text,
                        color = if (selectedDirection == firstOption)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.onSurface
                    )
                }
            },
            trailingContent = {
                Box(modifier = Modifier.padding(end = 20.dp)) {
                    RadioButton(
                        selected = selectedDirection == firstOption,
                        onClick = { onDirectionSelected(firstOption) }
                    )
                }
            }
        )

        ListItem(
            modifier = Modifier
                .clickable { onDirectionSelected(secondOption) }
                .height(48.dp),
            headlineContent = {
                Box(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Text(
                        secondOption.text,
                        color = if (selectedDirection == secondOption)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.onSurface
                    )
                }
            },
            trailingContent = {
                Box(modifier = Modifier.padding(end = 20.dp)) {
                    RadioButton(
                        selected = selectedDirection == secondOption,
                        onClick = { onDirectionSelected(secondOption) }
                    )
                }
            }
        )
    }
}