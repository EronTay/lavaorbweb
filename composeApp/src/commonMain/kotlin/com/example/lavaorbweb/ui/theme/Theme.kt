package com.example.lavaorbweb.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun LavaOrbTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        typography = getTypography(),
        content = content
    )
}