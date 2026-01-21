package com.ifochka.jufk.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun JufkTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        content = {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background,
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.TopCenter,
                ) {
                    Box(
                        modifier = Modifier.widthIn(max = 1250.dp).fillMaxSize(),
                    ) {
                        content()
                    }
                }
            }
        },
        colorScheme = JufkColorScheme,
    )
}

val KotlinPurple = Color(0xFF8A45FC)
val JufkColorScheme = darkColorScheme(
    background = Color.Black,
    onBackground = Color.White,
    primary = KotlinPurple,
)
