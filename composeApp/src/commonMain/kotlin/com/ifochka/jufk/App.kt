package com.ifochka.jufk

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ifochka.jufk.data.Content
import com.ifochka.jufk.ui.JufkTheme
import com.ifochka.jufk.ui.components.Footer
import com.ifochka.jufk.ui.components.HeroSection
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    JufkTheme {
        Scaffold(
            bottomBar = { Footer(links = Content.SocialLinks) },
        ) { paddingValues ->
            Box(
                modifier = Modifier.fillMaxSize().background(Color.Black).padding(paddingValues),
                contentAlignment = Alignment.TopCenter,
            ) {
                HeroSection()
            }
        }
    }
}
