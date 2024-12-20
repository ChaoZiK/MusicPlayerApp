package com.example.musicplayer.ui.components.search

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.musicplayer.ui.components.buttons.BackButton
import com.example.musicplayer.ui.theme.Dimensions

@Composable
fun SearchBar(
    searchText: String,
    onBackClick: () -> Unit,
    onSearchTextChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .statusBarsPadding()
            .fillMaxWidth()
            .padding(
                start = Dimensions.paddingMedium,
                end = Dimensions.paddingMedium,
                top = 12.dp,
                bottom = Dimensions.paddingSmall
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BackButton(
                onBackClick = onBackClick,
                modifier = Modifier.size(40.dp)
            )

            SearchTextField(
                searchText = searchText,
                onSearchTextChanged = onSearchTextChanged,
                modifier = Modifier.weight(1f)
            )
        }
    }
}