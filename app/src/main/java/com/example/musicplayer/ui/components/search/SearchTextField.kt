package com.example.musicplayer.ui.components.search

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musicplayer.R

@Composable
fun SearchTextField(
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    BasicTextField(
        value = searchText,
        onValueChange = onSearchTextChanged,
        modifier = modifier
            .height(40.dp)
            .animateContentSize(),
        singleLine = true,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
        textStyle = TextStyle(
            color = MaterialTheme.colorScheme.onSurface,
            textDecoration = TextDecoration.None,
            fontSize = 16.sp,
        ),
        visualTransformation = VisualTransformation.None,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            autoCorrectEnabled = false,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next,
        ),
        decorationBox = { innerTextField ->
            SearchTextFieldDecoration(
                searchText = searchText,
                onSearchTextChanged = onSearchTextChanged,
                innerTextField = innerTextField
            )
        }
    )
}

@Composable
private fun SearchTextFieldDecoration(
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    innerTextField: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f),
                RoundedCornerShape(16.dp)
            )
            .padding(start = 16.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterStart
        ) {
            if (searchText.isEmpty()) {
                SearchPlaceholder()
            }
            innerTextField()
        }
        if (searchText.isNotEmpty()) {
            ClearButton(
                onClear = { onSearchTextChanged("") }
            )
        }
    }
}

@Composable
private fun SearchPlaceholder() {
    Text(
        text = stringResource(R.string.search_placeholder),
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
    )
}