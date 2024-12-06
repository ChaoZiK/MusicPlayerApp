package com.example.musicplayer.ui.components.shared

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.musicplayer.ui.components.cards.MusicIconCard
import com.example.musicplayer.ui.theme.AppIcons
import com.example.musicplayer.ui.theme.Dimensions

@Composable
private fun TitleSubtitleContent(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun BaseItem(
    title: String,
    subtitle: String,
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier,
    leftContent: @Composable () -> Unit = { MusicIconCard() }
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        leftContent()

        TitleSubtitleContent(
            title = title,
            subtitle = subtitle,
            modifier = Modifier
                .weight(1f)
                .padding(start = Dimensions.paddingMedium)
        )

        IconButton(onClick = onMoreClick) {
            Icon(
                painter = painterResource(AppIcons.moreDots),
                contentDescription = "More options",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}