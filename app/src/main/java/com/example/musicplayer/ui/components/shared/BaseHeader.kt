package com.example.musicplayer.ui.components.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.musicplayer.ui.components.buttons.SortButton
import com.example.musicplayer.ui.theme.Dimensions

enum class HeaderActionType {
    ADD,
    SORT
}

@Composable
fun BaseHeader(
    count: Int,
    title: String,
    actionType: HeaderActionType,
    onActionClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimensions.paddingMedium),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$count $title",
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            ),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        when (actionType) {
            HeaderActionType.ADD -> {
                IconButton(onClick = onActionClick) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = "Add $title",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            HeaderActionType.SORT -> {
                SortButton(onClick = onActionClick)
            }
        }
    }
}