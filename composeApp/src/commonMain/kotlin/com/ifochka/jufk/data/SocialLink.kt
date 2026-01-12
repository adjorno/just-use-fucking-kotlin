package com.ifochka.jufk.data

import org.jetbrains.compose.resources.DrawableResource

data class SocialLink(
    val icon: DrawableResource,
    val name: String,
    val url: String,
)
