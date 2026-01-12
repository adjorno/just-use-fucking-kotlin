package com.ifochka.jufk.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun JufkTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        content = content,
        colorScheme = JufkColorScheme,
    )
}

val KotlinPurple = Color(0xFF8A45FC)
val JufkColorScheme = darkColorScheme(
    background = Color.Black,
    onBackground = Color.White,
    primary = KotlinPurple,
)
