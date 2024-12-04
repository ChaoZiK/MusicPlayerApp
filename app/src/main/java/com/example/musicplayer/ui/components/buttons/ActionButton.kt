@file:JvmName("ActionButtonKt")

package com.example.musicplayer.ui.components.buttons

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ActionButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .height(56.dp)
            .padding(vertical = 4.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        ),
        shape = MaterialTheme.shapes.large
    ) {
        Text(text)
    }
}
