package com.example.musicplayer.ui.components.search

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.musicplayer.R
import com.example.musicplayer.ui.theme.AppIcons

@Composable
fun SearchBackButton(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onBackClick,
        modifier = modifier.size(40.dp)
    ) {
        Icon(
            painter = painterResource(id = AppIcons.arrowBack),
            contentDescription = stringResource(id = R.string.back),
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun ClearButton(
    onClear: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClear,
        modifier = modifier.size(40.dp)
    ) {
        Icon(
            painter = painterResource(AppIcons.crossCircle),
            contentDescription = "Clear",
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
            modifier = Modifier.size(18.dp)
        )
    }
}