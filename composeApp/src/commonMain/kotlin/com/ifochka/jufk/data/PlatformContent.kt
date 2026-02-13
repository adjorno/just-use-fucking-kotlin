package com.ifochka.jufk.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Cta {
    data class Link(
        val text: String,
        val url: String,
    ) : Cta()

    data class Button(
        val text: String,
        val url: String,
        val icon: ImageVector?,
    ) : Cta()

    data class Code(
        val code: String,
    ) : Cta()
}

data class PlatformSection(
    val id: String,
    val title: String,
    val content: String,
    val icon: ImageVector,
    val iconTint: Color,
    val cta: Cta,
    val isHighlighted: Boolean,
)

data class InspirationLink(
    val name: String,
    val url: String,
)
